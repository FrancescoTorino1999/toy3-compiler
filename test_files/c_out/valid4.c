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

void func_moltiplicazione(double x, double y, double *res, bool *grande);
char*  func_saluto();
double sommagrande = 0.0;
double sommapiccola = 0.0;
int i = 0;
double x = 0.0;
double y = 0.0;
double risultato = 0.0;
bool grande = false;
bool nonusata = false;
void func_moltiplicazione (double x, double y, double *res, bool *grande)
{
double risultato = 0.0;
double nonusata = 0.0;
if(x * y >= 100){
*grande = true;
} else {
*grande = false;
}
*res = risultato;
}
char* func_saluto ()
{
return "ciao";
}
int main() {
sommagrande = 0;
sommapiccola = 0;
printf("%s\n", "Questo programma permette di svolgere una serie di moltiplicazioni");
printf("%s\n", "sommando i risultati < 100 in sommagrande e quelli < 100 in sommapiccola");
i = -1;
while(i <= 0)
{
char saluto[MAXSIZE] = "ciao";
printf("%s", "Quante moltiplicazioni vuoi svolgere? (inserire intero > 0)");
scanf("%d", &i);
printf("%s\n", saluto);
}
while(i > 0)
{
x = -1;
y = -1;
while(!(x > 0 && y > 0))
{
char saluto[MAXSIZE] = "byebye";
printf("%s\n", "Moltiplicazione ");
printf("%d\n", i);
printf("%s\n", ": inserisci due numeri positivi");
scanf("%lf", &x);
scanf("%lf", &y);
printf("%s\n", saluto);
}
func_moltiplicazione(x, y, &risultato, &grande);
printf("%lf\n", risultato);
if(grande){
printf("%s\n", "il risultato è grande");
sommagrande = sommagrande + risultato;
} else {
printf("%s\n", "il risultato è piccolo");
sommapiccola = sommapiccola + risultato;
}
i = i - 1;
}
printf("%s\n", "\n sommagrande è ");
printf("%lf\n", sommagrande);
printf("%s\n", "sommapiccola è ");
printf("%lf\n", sommapiccola);
}
