package ir.eynajgroup.dribble2goal.Util;

/**
 * Created by kAvEh on 2/23/2016.
 */
public class Random {
    private static java.util.Random rand;

    public static boolean random_boolean()
    {
        rand = new java.util.Random();
        return rand.nextBoolean();
    }

    public static void random_byte(byte[] paramArrayOfByte)
    {
        rand = new java.util.Random();
        rand.nextBytes(paramArrayOfByte);
    }

    public static void random_byte(byte[] paramArrayOfByte, int paramInt)
    {
        rand = new java.util.Random();
        paramArrayOfByte = new byte[paramInt];
        rand.nextBytes(paramArrayOfByte);
    }

    public static double random_double()
    {
        rand = new java.util.Random();
        return rand.nextDouble();
    }

    public static double random_double(double paramDouble1, double paramDouble2)
    {
        rand = new java.util.Random();
        return (paramDouble2 - paramDouble1) * rand.nextDouble() + paramDouble1;
    }

    public static float random_float()
    {
        rand = new java.util.Random();
        return rand.nextFloat();
    }

    public static float random_float(float paramFloat1, float paramFloat2)
    {
        rand = new java.util.Random();
        return rand.nextFloat() * (paramFloat2 - paramFloat1) + paramFloat1;
    }

    public static double random_gaussian()
    {
        rand = new java.util.Random();
        return rand.nextGaussian();
    }

    public static double random_gaussian(double paramDouble1, double paramDouble2)
    {
        rand = new java.util.Random();
        return (paramDouble2 - paramDouble1) * rand.nextGaussian() + paramDouble1;
    }

    public static int random_int()
    {
        rand = new java.util.Random();
        return rand.nextInt();
    }

    public static int random_int(int paramInt1, int paramInt2)
    {
        rand = new java.util.Random();
        return rand.nextInt(paramInt2 - paramInt1 + 1) + paramInt1;
    }

    public static long random_long()
    {
        rand = new java.util.Random();
        return rand.nextLong();
    }

    public static long random_long(long paramLong1, long paramLong2)
    {
        rand = new java.util.Random();
        return (paramLong2 - paramLong1) * rand.nextLong() + paramLong1;
    }
}

