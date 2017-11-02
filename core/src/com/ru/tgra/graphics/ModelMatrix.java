package com.ru.tgra.graphics;

public class ModelMatrix extends Matrix {
	
	public static ModelMatrix main;

	private float[] MMtmp;

	public ModelMatrix()
	{
		super();
		MMtmp = new float[16];
	}

	public Vector3D getA()
	{
		return new Vector3D(matrix.get(0), matrix.get(1), matrix.get(2));
	}

	public Vector3D getB()
	{
		return new Vector3D(matrix.get(4), matrix.get(5), matrix.get(6));
	}

	public Vector3D getC()
	{
		return new Vector3D(matrix.get(8), matrix.get(9), matrix.get(10));
	}

	public Point3D getOrigin()
	{
		return new Point3D(matrix.get(12), matrix.get(13), matrix.get(14));
	}

	public void addTranslationBaseCoords(float Tx, float Ty, float Tz)
	{
		matrix.put(12, matrix.get(12) + Tx);
		matrix.put(13, matrix.get(13) + Ty);
		matrix.put(14, matrix.get(14) + Tz);
	}

	public void addTranslation(float Tx, float Ty, float Tz)
	{
		matrix.put(12, matrix.get(0)*Tx + matrix.get(4)*Ty + matrix.get(8)*Tz + matrix.get(12));
		matrix.put(13, matrix.get(1)*Tx + matrix.get(5)*Ty + matrix.get(9)*Tz + matrix.get(13));
		matrix.put(14, matrix.get(2)*Tx + matrix.get(6)*Ty + matrix.get(10)*Tz + matrix.get(14));
	}

	public void addScale(float Sx, float Sy, float Sz)
	{
		matrix.put(0, Sx * matrix.get(0));
		matrix.put(1, Sx * matrix.get(1));
		matrix.put(2, Sx * matrix.get(2));

		matrix.put(4, Sy * matrix.get(4));
		matrix.put(5, Sy * matrix.get(5));
		matrix.put(6, Sy * matrix.get(6));

		matrix.put(8, Sz * matrix.get(8));
		matrix.put(9, Sz * matrix.get(9));
		matrix.put(10, Sz * matrix.get(10));
	}

	public void addRotationZ(float angle)
	{
		float c = (float)Math.cos((double)angle * Math.PI / 180.0);
		float s = (float)Math.sin((double)angle * Math.PI / 180.0);

		MMtmp[0] = c; MMtmp[4] = -s; MMtmp[8] = 0; MMtmp[12] = 0;
		MMtmp[1] = s; MMtmp[5] = c; MMtmp[9] = 0; MMtmp[13] = 0;
		MMtmp[2] = 0; MMtmp[6] = 0; MMtmp[10] = 1; MMtmp[14] = 0;
		MMtmp[3] = 0; MMtmp[7] = 0; MMtmp[11] = 0; MMtmp[15] = 1;

		this.addTransformation(MMtmp);
	}

	public void addRotationY(float angle)
	{
		float c = (float)Math.cos((double)angle * Math.PI / 180.0);
		float s = (float)Math.sin((double)angle * Math.PI / 180.0);

		MMtmp[0] = c; MMtmp[4] = 0; MMtmp[8] = s; MMtmp[12] = 0;
		MMtmp[1] = 0; MMtmp[5] = 1; MMtmp[9] = 0; MMtmp[13] = 0;
		MMtmp[2] = -s; MMtmp[6] = 0; MMtmp[10] = c; MMtmp[14] = 0;
		MMtmp[3] = 0; MMtmp[7] = 0; MMtmp[11] = 0; MMtmp[15] = 1;

		this.addTransformation(MMtmp);
	}

	public void addRotationX(float angle)
	{
		float c = (float)Math.cos((double)angle * Math.PI / 180.0);
		float s = (float)Math.sin((double)angle * Math.PI / 180.0);

		MMtmp[0] = 1; MMtmp[4] = 0; MMtmp[8] = 0; MMtmp[12] = 0;
		MMtmp[1] = 0; MMtmp[5] = c; MMtmp[9] = -s; MMtmp[13] = 0;
		MMtmp[2] = 0; MMtmp[6] = s; MMtmp[10] = c; MMtmp[14] = 0;
		MMtmp[3] = 0; MMtmp[7] = 0; MMtmp[11] = 0; MMtmp[15] = 1;

		this.addTransformation(MMtmp);
	}

	public void addRotation(float angle, Vector3D rotationVector)
	{
		rotationVector.normalize();
		float x = rotationVector.x;
		float y = rotationVector.y;
		float z = rotationVector.z;
		float c = (float)Math.cos((double)angle * Math.PI / 180.0);
		float s = (float)Math.sin((double)angle * Math.PI / 180.0);

		MMtmp[0] = c+(1-c)*x*x; MMtmp[4] = (1-c)*y*x-s*z; MMtmp[8] = (1-c)*z*x+s*y; MMtmp[12] = 0;
		MMtmp[1] = (1-c)*x*y+s*z; MMtmp[5] = c+(1-c)*y*y; MMtmp[9] = (1-c)*z*y-s*x; MMtmp[13] = 0;
		MMtmp[2] = (1-c)*x*z-s*y; MMtmp[6] = (1-c)*y*z+s*x; MMtmp[10] = c+(1-c)*z*z; MMtmp[14] = 0;
		MMtmp[3] = 0; MMtmp[7] = 0; MMtmp[11] = 0; MMtmp[15] = 1;

		this.addTransformation(MMtmp);
	}

	public void addRotationRadians(float radians, Vector3D rotationVector)
	{
		rotationVector.normalize();
		float x = rotationVector.x;
		float y = rotationVector.y;
		float z = rotationVector.z;
		float c = (float)Math.cos((double)radians);
		float s = (float)Math.sin((double)radians);

		MMtmp[0] = c+(1-c)*x*x; MMtmp[4] = (1-c)*y*x-s*z; MMtmp[8] = (1-c)*z*x+s*y; MMtmp[12] = 0;
		MMtmp[1] = (1-c)*x*y+s*z; MMtmp[5] = c+(1-c)*y*y; MMtmp[9] = (1-c)*z*y-s*x; MMtmp[13] = 0;
		MMtmp[2] = (1-c)*x*z-s*y; MMtmp[6] = (1-c)*y*z+s*x; MMtmp[10] = c+(1-c)*z*z; MMtmp[14] = 0;
		MMtmp[3] = 0; MMtmp[7] = 0; MMtmp[11] = 0; MMtmp[15] = 1;

		this.addTransformation(MMtmp);
	}


	public void addRotationQuaternion(float x, float y, float z, float w)
	{
		MMtmp[0] = 1 - 2*y*y - 2*z*z; MMtmp[4] = 2*x*y - 2*w*z; MMtmp[8] = 2*x*z + 2*y*w; MMtmp[12] = 0;
		MMtmp[1] = 2*x*y + 2*z*w; MMtmp[5] = 1 - 2*x*x - 2*z*z; MMtmp[9] = 2*y*z - 2*x*w; MMtmp[13] = 0;
		MMtmp[2] = 2*x*z - 2*y*w; MMtmp[6] = 2*y*z + 2*x*w; MMtmp[10] = 1 - 2*x*x - 2*y*y; MMtmp[14] = 0;
		MMtmp[3] = 0; MMtmp[7] = 0; MMtmp[11] = 0; MMtmp[15] = 1;

		this.addTransformation(MMtmp);
	}
}
