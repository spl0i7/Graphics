#include<GL/gl.h>
#include<GL/glut.h>

float angle = 1.0;

float vert[][4]={
	{10.0f,10.0f,10.0f},
	{-10.0f,10.0f,10.0f},
	{-10.0f,-10.0f,10.0f},
	{10.0f,-10.0f,10.0f},

	{10.0f,30.0f,-10.0f},
	{-10.0f,10.0f,-10.0f},
	{-10.0f,-10.0f,-10.0f},
	{10.0f,-10.0f,-10.0f},
};

float color[][4] = {
	{1.0f,0.0f,0.0f,0.0f},	
	{0.0f,1.0f,0.0f,0.0f},	
	{0.0f,0.0f,1.0f,0.0f},	
	{1.0f,1.0f,0.0f,0.0f},	
	{0.0f,1.0f,1.0f,0.0f},	
	{1.0f,0.0f,1.0f,0.0f},	
	{1.0f,1.0f,1.0f,0.0f},	
};


void draw_cube(void) {
	glRotatef(angle,0.0,1.0,1.0);
	glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
	glBegin(GL_QUADS);
		glColor3fv(color[0]);
		glVertex3fv(vert[0]);	
		glVertex3fv(vert[1]);
		glVertex3fv(vert[2]);
		glVertex3fv(vert[3]);


		glColor3fv(color[1]);
		glVertex3fv(vert[4]);
		glVertex3fv(vert[5]);
		glVertex3fv(vert[6]);
		glVertex3fv(vert[7]);

		
		glColor3fv(color[2]);
		glVertex3fv(vert[0]);
		glVertex3fv(vert[4]);
		glVertex3fv(vert[7]);
		glVertex3fv(vert[3]);


		glColor3fv(color[3]);
		glVertex3fv(vert[1]);
		glVertex3fv(vert[5]);
		glVertex3fv(vert[6]);
		glVertex3fv(vert[2]);


		glColor3fv(color[4]);
		glVertex3fv(vert[4]);
		glVertex3fv(vert[5]);
		glVertex3fv(vert[1]);
		glVertex3fv(vert[0]);


		glColor3fv(color[5]);
		glVertex3fv(vert[3]);
		glVertex3fv(vert[2]);
		glVertex3fv(vert[6]);
		glVertex3fv(vert[7]);


	glEnd();
	glutSwapBuffers();

}

int main(int argc, char **argv) {
	glutInit(&argc, argv);
	glutInitWindowSize(500,500);
	glutInitDisplayMode(GLUT_DOUBLE | GLUT_DEPTH);
	glutCreateWindow("CUBE");
	glutDisplayFunc(draw_cube);
	glutIdleFunc(draw_cube);
	glLoadIdentity();
	glOrtho(-30.0,30.0,-30.0,30.0,-30.0,30.0);
	glEnable(GL_DEPTH_TEST);
	glRotatef(30.0,1.0,0.0,0.0);
	glClearColor(0.0,0.0,0.0,0.0);
	glutMainLoop();
	return 0;


}
