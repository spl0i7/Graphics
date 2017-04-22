#include <math.h>
#include <GL/glut.h>
#include <stdio.h>
#include <stdlib.h>
float ptsIni[8][3]= { 
	{80,80,-100}, //0
	{180,80,-100}, //1
	{180,180,-100}, //2
	{80,180,-100}, //3
	{60,60,0}, //4
	{160,60,0}, //5
	{160,160,0}, //6
	{60,160,0}//7
};

float ptsFin[8][3];
float TransDistX,TransDistY,TransDistZ;
//Translations along Axes
float ScaleX,ScaleY,ScaleZ;
float Alpha;
int choice,choiceRot,choiceRef;

void Translate(int tx, int ty, int tz) {
	for(int i = 0 ; i < 8 ; ++i) {
			ptsFin[i][0] = ptsIni[i][0] + tx;
			ptsFin[i][1] = ptsIni[i][1] + ty;
			ptsFin[i][2] = ptsIni[i][2] + tz;
	}
}
void Scale(float sx , float sy ,float sz) {
	for(int i = 0 ; i < 8 ; ++i) {
			ptsFin[i][0] = ptsIni[i][0]*sx;
			ptsFin[i][1] = ptsIni[i][1]*sy;
			ptsFin[i][2] = ptsIni[i][2]*sz;
	}
}
void RotateX(float angle) {
	float ang = angle * M_PI / 180.0;
	for(int i = 0 ; i < 8 ; ++i) {
			ptsFin[i][0] = ptsIni[i][0];
			ptsFin[i][1] = ptsIni[i][1]*cos(ang) - ptsIni[i][2]*sin(ang);
			ptsFin[i][2] = ptsIni[i][1]*sin(ang) + ptsIni[i][2]*cos(ang);
	}
}

void Axes(void) {
	glColor3f (0.0, 0.0, 0.0);
	glBegin(GL_LINES);
		glVertex2s(-1000 ,0);
		glVertex2s( 1000 ,0);
		glVertex2s(0 ,-1000);
		glVertex2s(0 , 1000);
	glEnd();
}
void Draw(float a[8][3]) {
	glBegin(GL_QUADS);
		glColor3f (0.7, 0.4, 0.7);
		glVertex3fv(a[0]);
		glVertex3fv(a[1]);
		glVertex3fv(a[2]);
		glVertex3fv(a[3]);
		
		glColor3f (0.8, 0.6, 0.5);
		glVertex3fv(a[0]);
		glVertex3fv(a[1]);
		glVertex3fv(a[5]);
		glVertex3fv(a[4]);
		
		glColor3f (0.2, 0.4, 0.7);
		glVertex3fv(a[0]);
		glVertex3fv(a[3]);
		glVertex3fv(a[7]);
		glVertex3fv(a[4]);
		
		glColor3f (0.5, 0.4, 0.3);
		glVertex3fv(a[1]);
		glVertex3fv(a[2]);
		glVertex3fv(a[6]);
		glVertex3fv(a[5]);
		
		glColor3f (0.5, 0.6, 0.2);
		glVertex3fv(a[2]);
		glVertex3fv(a[3]);
		glVertex3fv(a[7]);
		glVertex3fv(a[6]);
		
		glColor3f (0.7, 0.3, 0.4);
		glVertex3fv(a[4]);
		glVertex3fv(a[5]);
		glVertex3fv(a[6]);
		glVertex3fv(a[7]);
	glEnd();
}
void display(void) {
	glClear (GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
	Axes();
	glColor3f (1.0, 0.0, 0.0);
	Draw(ptsIni);
	
	switch(choice) {
		case 1:   
			Translate(TransDistX , TransDistY ,TransDistZ);
			break;
		case 2:   
			Scale(ScaleX, ScaleY, ScaleZ);
			break;
		case 3:   
		
			RotateX(Alpha);
			break;
	}
	Draw(ptsFin);
	glFlush();
}

int main (int argc, char **argv) {
	printf("Enter your choice number:\n1.Translation\n2.Scaling\n3.Rotate about X\n=>");
	scanf("%d",&choice);
	switch(choice) {
		case 1:
			printf("Enter Translation along X, Y & Z\n=>");
			scanf("%f%f%f",&TransDistX , &TransDistY , &TransDistZ);
			break;
		case 2:
			printf("Enter Scaling ratios along X, Y & Z\n=>");
			scanf("%f%f%f",&ScaleX , &ScaleY , &ScaleZ);
			break;
		case 3:
			printf("Enter Rot. Angle Alpha: ");
			scanf("%f",&Alpha);
			break;
		}
	glutInit(&argc, argv);
	glutInitDisplayMode ( GLUT_DEPTH);
	glutInitWindowSize (1366, 720);
	glutCreateWindow (" Basic Transformations ");
	glClearColor (1.0, 1.0, 1.0, 1.0);
	glOrtho(-454.0, 454.0, -250.0, 250.0, -250.0, 250.0);
	// Set the no. of Co-ordinates along X & Y axes and their gappings
	glEnable(GL_DEPTH_TEST);
	glutDisplayFunc(display);
	glutMainLoop();
	return 0;
}
