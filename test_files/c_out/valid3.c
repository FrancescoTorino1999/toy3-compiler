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

void func_scoping(int n, int m, char* message);
int  func_glob();
char message[MAXSIZE] = "level 0";
int n = 0;
int m = 0;
int k = 0;
void func_scoping (int n, int m, char* message)
{
strcpy(message, "level 1");
if(n <= 1){
strcpy(message, "level 2.1");
if(m <= 1){
strcpy(message, "level 3.1");
} else {
if(m > 1 && m < 5){
strcpy(message, "level 3.2");
} else {
strcpy(message, "level 3.3");
}
}
} else {
strcpy(message, "level 2.2");
if(m <= 1){
strcpy(message, "level 3.4");
} else {
if(m > 1 && m < 5){
strcpy(message, "level 3.5");
} else {
strcpy(message, "level 3.6");
}
}
}
}
int func_glob ()
{
return 100;
}
int main() {
k = 6;
while(k >= 1)
{
printf("%s", "Inserisci n: ");
scanf("%d", &n);
printf("%s", "Inserisci m: ");
scanf("%d", &m);
printf("%s\n", "I valori inseriti sono ");
printf("%d\n", n);
printf("%s\n", " e ");
printf("%d\n", m);
func_scoping(n, m, message);
k = k - 1;
}
printf("%s\n", message);
printf("%d\n", func_glob());
}
