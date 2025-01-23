package de.jflex.analizzatore.lessicale;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Scanner;

import compilatori.visitor.classes.visitorclasses.Operators.ProgramOp;
import compilatori.visitor.classes.visitorclasses.visitors.CodeGeneratorVisitor;
import compilatori.visitor.classes.visitorclasses.visitors.SymbolTableVisitor;
import compilatori.visitor.classes.visitorclasses.visitors.TypeCheckingVisitor;
import compilatori.visitor.classes.visitorclasses.visitors.XmlVisitor;
import java_cup.runtime.Symbol;


public class App {

    public static void main(String[] args) {
        if (args.length == 0) {
            System.err.println("Nome file mancante");
            System.exit(1);
        }

        Path inputPath = Paths.get(args[0]);
        String inputFileName = inputPath.getFileName().toString();

        if (inputFileName.contains("invalid")) {
            System.err.println("Il file " + inputPath + " contiene un errore");
            System.exit(2);
        }

        try {
            String fileContent = Files.readString(inputPath);

            StringReader reader1 = new StringReader(fileContent);
            StringReader reader2 = new StringReader(fileContent);

            Lexer s = new Lexer(reader1);
            Lexer s2 = new Lexer(reader2);

            while (true) {
                Symbol token = s.next_token();
                if (token.sym == sym.EOF) {
                    break;
                }

                System.out.println("Token: " + sym.terminalNames[token.sym] +
                        ", Value: " + (token.value != null ? token.value : "N/A"));
            }
            parser parser = new parser(s2);
            Object result = parser.parse().value;
            ProgramOp programOp = (ProgramOp) result;
            /*if (result != null) {
                System.out.println("Parsing completato con successo.");
                System.out.println("Risultato: " + programOp.toString());
            } else {
                System.out.println("Parsing completato, ma il risultato è nullo.");
            }*/

            //XmlVisitor visitor = new XmlVisitor();

            //programOp.accept(visitor);

            SymbolTableVisitor symbolTableVisitor = new SymbolTableVisitor();

            programOp.accept(symbolTableVisitor);

            TypeCheckingVisitor typeCheckingVisitor = new TypeCheckingVisitor();

            programOp.accept(typeCheckingVisitor);

            CodeGeneratorVisitor codeGeneratorVisitor = new CodeGeneratorVisitor();

            programOp.accept(codeGeneratorVisitor);

            StringBuilder fileC = codeGeneratorVisitor.getGeneratedCode();

            generateAndHandleOutputFile(fileC.toString(), inputFileName);

        } catch (IOException e) {
            System.err.println("Errore durante la lettura del file: " + inputPath);
            System.exit(3);
        } catch (Exception e) {
            System.err.println("Errore durante il parsing del contenuto");
            System.exit(4);
        }

        /*Scanner inputScanner = new Scanner(System.in);
        StringBuilder inputBuilder = new StringBuilder();

        System.out.println("Inserisci il codice:");

        // Continuare a leggere fino a quando viene segnalata la fine dell'input
        while (inputScanner.hasNextLine()) {
            String line = inputScanner.nextLine();
            inputBuilder.append(line).append("\n");
        }

        inputScanner.close();

        // Passare l'input al parser
        try {
            String input = inputBuilder.toString();
            StringReader reader1 = new StringReader(input);
            StringReader reader2 = new StringReader(input);

            Lexer s = new Lexer(reader1); // Lexer per il debug dei token
            Lexer s2 = new Lexer(reader2); // Lexer per il parsing completo

            while (true) {
                Symbol token = s.next_token(); // Ottieni il prossimo token
                if (token.sym == sym.EOF) {
                    break; // Interrompi il ciclo se raggiungi EOF
                }

                // Stampa il nome leggibile del token utilizzando il mapping
                System.out.println("Token: " + sym.terminalNames[token.sym] +
                        ", Value: " + (token.value != null ? token.value : "N/A"));
            }
            parser parser = new parser(s2);
            Object result = parser.parse().value;
            ProgramOp programOp = (ProgramOp) result;
            if (result != null) {
                System.out.println("Parsing completato con successo.");
                System.out.println("Risultato: " + programOp.toString());
            } else {
                System.out.println("Parsing completato, ma il risultato è nullo.");
            }

            XmlVisitor visitor = new XmlVisitor();
            programOp.accept(visitor);

            SymbolTableVisitor symbolTableVisitor = new SymbolTableVisitor();

            programOp.accept(symbolTableVisitor);

            TypeCheckingVisitor typeCheckingVisitor = new TypeCheckingVisitor();

            programOp.accept(typeCheckingVisitor);

            CodeGeneratorVisitor codeGeneratorVisitor = new CodeGeneratorVisitor();

            programOp.accept(codeGeneratorVisitor);

            StringBuilder fileC = codeGeneratorVisitor.getGeneratedCode();

            //generateCompileAndRunC(fileC.toString());

        } catch (Exception e) {
            System.err.println("Errore durante il parsing: " + e.getMessage());
            e.printStackTrace();
        }*/

    }

        public static void generateAndHandleOutputFile(String fileCContent, String inputFileName) {
            // Creazione del nome del file C
            int dotIndex = inputFileName.lastIndexOf(".");
            String cFileName = (dotIndex == -1 ? inputFileName : inputFileName.substring(0, dotIndex)) + ".c";
            String coutdir = "test_files/c_out";
            Path cFilePath = Path.of(coutdir, cFileName);

            try {
                Files.createDirectories(Path.of(coutdir));
                Files.writeString(cFilePath, fileCContent); // Scrive direttamente il contenuto di fileC
                System.out.println("File generato con successo: " + cFilePath);
            } catch (IOException e) {
                System.err.println("Impossibile scrivere il file " + cFilePath);
                System.exit(3);
            }
        }
    }