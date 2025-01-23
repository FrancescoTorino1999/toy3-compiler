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

void func_somma(double input1, double input2, double *result);
void func_sottrazione(double input1, double input2, double *result);
double  func_moltiplicazione(double input1, double input2);
double  func_divisione(double input1, double input2);
void func_tutteleoperazioni(double input1, double input2, double *res1, double *res2, double *res3, double *res4);
char*  func_stampa(char* stringa);
char operazione[MAXSIZE];
double input1 = 0.0;
double input2 = 0.0;
double answer = 0.0;
double result = 0.0;
double res1 = 0.0;
double res2 = 0.0;
double res3 = 0.0;
double res4 = 0.0;
bool flag = false;
void func_somma (double input1, double input2, double *result)
{
*result = input1 + input2;
}
void func_sottrazione (double input1, double input2, double *result)
{
*result = input1 - input2;
}
double func_moltiplicazione (double input1, double input2)
{
return input1 * input2;
}
double func_divisione (double input1, double input2)
{
if(input2 == 0){
printf("%s\n", "Errore: divisione per zero");
return 0.0;
}
return input1 / input2;
}
void func_tutteleoperazioni (double input1, double input2, double *res1, double *res2, double *res3, double *res4)
{
func_somma(input1, input2, res1);
func_sottrazione(input1, input2, res2);
*res3 = func_divisione(input1, input2);
*res4 = func_moltiplicazione(input1, input2);
}
char* func_stampa (char* stringa)
{
return str_concat("Ciao! ", stringa);
}
int main() {
flag = true;
while(flag)
{
printf("%s", "Inserisci l'operazione da effettuare (somma, sottrazione, divisione, moltiplicazione, tutteleoperazioni): ");
scanf("%s", operazione);
printf("%s", "Inserisci il primo input: ");
scanf("%lf", &input1);
printf("%s", "Inserisci il secondo input: ");
scanf("%lf", &input2);
printf("%s\n", "Hai scelto l'operazione ");
printf("%s\n", operazione);
printf("%s\n", " con gli argomenti ");
printf("%lf\n", input1);
printf("%s\n", " e ");
printf("%lf\n", input2);
if((strcmp(operazione, "somma") == 0)){
func_somma(input1, input2, &result);
} else {
if((strcmp(operazione, "sottrazione") == 0)){
func_sottrazione(input1, input2, &result);
} else {
if((strcmp(operazione, "divisione") == 0)){
result = func_divisione(input1, input2);
} else {
if((strcmp(operazione, "moltiplicazione") == 0)){
result = func_moltiplicazione(input1, input2);
} else {
if((strcmp(operazione, "tutteleoperazioni") == 0)){
func_tutteleoperazioni(input1, input2, &res1, &res2, &res3, &res4);
} else {
printf("%s\n", "Operazione non consentita");
}
}
}
}
}
if((strcmp(operazione, "tutteleoperazioni") != 0)){
printf("%s\n", "Il risultato è: ");
printf("%lf\n", result);
} else {
printf("%s\n", func_stampa(str_concat(str_concat(str_concat(str_concat(str_concat(str_concat(str_concat(str_concat("I risultati delle 4 operazioni sono \n", double_to_str(res1)), "\n"), double_to_str(res2)), "\n"), double_to_str(res3)), "\n"), double_to_str(res4)), "\n")));
}
printf("%s", "Vuoi continuare? (1 yes/0 no): ");
scanf("%lf", &answer);
if(answer == 1){
flag = true;
} else {
flag = false;
}
}
}
