#include <stdio.h>
#include <stdbool.h>
#include <string.h>
#include <stdlib.h>
#define MAXSIZE 100
char* integer_to_str(int i){
	int length= snprintf(NULL,0,"%d",i);
	char* result=malloc(length+1);
	snprintf(result,length+1,"%d",i);
	return result;
}
char* double_to_str(float i){
	int length= snprintf(NULL,0,"%f",i);
	char* result=malloc(length+1);
	snprintf(result,length+1,"%f",i);
	return result;
}
char* char_to_str(char i){
	int length= snprintf(NULL,0,"%c",i);
	char* result=malloc(length+1);
	snprintf(result,length+1,"%c",i);
	return result;
}
char* bool_to_str(bool i){
	int length= snprintf(NULL,0,"%d",i);
	char* result=malloc(length+1);
	snprintf(result,length+1,"%d",i);
	return result;
}
char* str_concat(char* str1, char* str2){
	char* result=malloc(sizeof(char)*MAXSIZE);
	result=strcat(result,str1);
	result=strcat(result,str2);
	return result;
}

char* read_str(){
	char* str=malloc(sizeof(char)*MAXSIZE);
	scanf("%s",str);
	return str;
}

int str_to_bool(char* expr){
	int i=0;
	if ( (strcmp(expr, "true")==0) || (strcmp(expr, "1"))==0 )
		i=1;
	if ( (strcmp(expr, "false")==0) || (strcmp(expr, "0"))==0 )
		i=0;
	return i;
}

void func_sommac(double a, double d, double b, char* size, double *result);
char*  func_stampa2(char* messaggio);
char*  func_stampa(char* messaggio);
char*  func_stampa2(char* messaggio);
int c = 1;
double a = 0.0;
double b = 0.0;
double x = 0.0;
char taglia[MAXSIZE];
char ans1[MAXSIZE];
char ans[MAXSIZE];
double risultato = 0.0;
void func_sommac (double a, double d, double b, char* size, double *result)
{
*result = a + b + c + d;
if(*result > 100){
strcpy(size, "grande");
} else {
if(*result > 50){
strcpy(size, "media");
} else {
strcpy(size, "piccola");
}
}
}
char* func_stampa (char* messaggio)
{
int i = 0;
while(i < 4)
{
printf("%s\n", "");
i = i + 1;
}
printf("%s\n", messaggio);
return "ok";
}
int main() {
a = 1;
b = 2.2;
x = 3;
risultato = 0.0;
strcpy(ans, "no");
func_sommac(a, x, b, taglia, &risultato);
func_stampa(str_concat(str_concat(str_concat(str_concat(str_concat(str_concat(str_concat("La somma di ", double_to_str(a)), " e "), double_to_str(b)), " incrementata di "), integer_to_str(c)), " è "), taglia));
func_stampa(str_concat("Ed è pari a ", double_to_str(risultato)));
printf("%s", "Vuoi continuare? (si/no) - inserisci due volte la risposta\n");
scanf("%s", ans);
scanf("%s", ans1);
{
char stringa[MAXSIZE] = "ciao";
char* func_stampa2 (char* messaggio)
{
return str_concat(messaggio, "a tutti");
}
{
while((strcmp(ans, "si") == 0))
{
printf("%s", "Inserisci un intero: ");
scanf("%lf", &a);
printf("%s", "Inserisci un reale: ");
scanf("%lf", &b);
printf("%s\n", stringa);
func_sommac(a, x, b, taglia, &risultato);
func_stampa(str_concat(str_concat(str_concat(str_concat(str_concat(str_concat(str_concat("La somma di ", double_to_str(a)), " e "), double_to_str(b)), " incrementata di "), integer_to_str(c)), " è "), taglia));
func_stampa(str_concat("Ed è pari a ", double_to_str(risultato)));
printf("%s\n", func_stampa2(str_concat(double_to_str(risultato), stringa)));
printf("%s", "Vuoi continuare? (si/no): ");
scanf("%s", ans);
}
printf("%s\n", stringa);
}
}
printf("%s\n", "");
printf("%s\n", "Ciao");
}
