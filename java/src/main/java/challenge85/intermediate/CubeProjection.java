package challenge85.intermediate;

/**
 * [2012/08/03] Challenge #85 [intermediate] (3D cuboid projection)
 * http://www.reddit.com/r/dailyprogrammer/comments/xq2ao/832012_challenge_85_intermediate_3d_cuboid/
 *
 * Write a program that outputs simple 3D ASCII art for a cuboid in an oblique perspective, given a length, height,
 * and depth, like this:
 * $ python 3d.py 20 10 3
 *
 *    :::::::::::::::::::/
 *   :::::::::::::::::::/+
 *  :::::::::::::::::::/++
 * ####################+++
 * ####################+++
 * ####################+++
 * ####################+++
 * ####################+++
 * ####################+++
 * ####################+++
 * ####################++
 * ####################+
 * ####################
 *
 * (The characters used for the faces (here #, :, and +) are fully up to you,
 * but make sure you don't forget the / on the top-right edge.)
 *
 * User: Filipe
 * Date: 2012/08/06
 */
public class CubeProjection {

    public static void main(String[] args) {

        drawCube(6, 4, 22);
    }

    public static void drawCube(final int width, final int height, final int depth) {

        for (int i = 0; i < height + depth; i++) {

            for (int j = 0; j < width + depth && (i + j <= width + height + depth - 2); j++) {

                if (i >= depth && j < width) {
                    System.out.print("#");
                } else if (i + j < depth) {
                    System.out.print(" ");
                } else if (j < width + depth - i - 1) {
                    System.out.print(":");
                } else if (j == width + depth - i - 1) {
                    System.out.print("/");
                } else {
                    System.out.print("+");
                }
            }

            System.out.println();
        }
    }
}
