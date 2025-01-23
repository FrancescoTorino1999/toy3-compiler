package compilatori.visitor.classes.visitorclasses.baseclasses;

import compilatori.visitor.classes.visitorclasses.exceptions.ScopingException;

import java.util.ArrayList;
import java.util.UUID;

public class SymbolTable implements Cloneable{

    private String id;
    private String classFathersName;
    private ArrayList<Symbol> symbols;

    public SymbolTable() {
    }

    public SymbolTable(String classFathersName, ArrayList<Symbol> symbols) {
        this.classFathersName = classFathersName;
        this.symbols = symbols;
        this.id = generateUniqueId(classFathersName);
    }

    private String generateUniqueId(String base) {
        double randomValue = Math.random(); // Numero casuale
        int hashValue = base.hashCode(); // Hash del contenuto
        String combined = randomValue + "_" + hashValue; // Combina random + hash in una stringa
        return UUID.nameUUIDFromBytes(combined.getBytes()).toString(); // Genera UUID dalla stringa combinata
    }

    public void setClassFathersName(String classFathersName) {
        this.classFathersName = classFathersName;
    }

    public void setSymbols(ArrayList<Symbol> symbols) {
        this.symbols = symbols;
    }

    public String getClassFathersName() {
        return classFathersName;
    }

    public ArrayList<Symbol> getSymbols() {
        return symbols;
    }

    public void addVariable(Symbol symbol) throws ScopingException {
        if (symbols.contains(symbol)) {
            throw new ScopingException(symbol.getSymbol());
        }
        symbols.add(symbol);
    }

    public String getId() {
        return id;
    }

    @Override
    public SymbolTable clone() {
        try {
            SymbolTable copy = (SymbolTable) super.clone();
            copy.setSymbols(new ArrayList<>(this.symbols)); // Copia profonda dei simboli
            return copy;
        } catch (CloneNotSupportedException e) {
            throw new AssertionError(); // Questo non dovrebbe mai accadere
        }
    }
}
