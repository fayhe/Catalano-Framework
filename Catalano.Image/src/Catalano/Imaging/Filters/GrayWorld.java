// Catalano Imaging Library
// The Catalano Framework
//
// Copyright © Diego Catalano, 2014
// diego.catalano at live.com
//
// Copyright © Juan Manuel Perez Rua, 2013
// juanmanpr at gmail.com
//
//    This library is free software; you can redistribute it and/or
//    modify it under the terms of the GNU Lesser General Public
//    License as published by the Free Software Foundation; either
//    version 2.1 of the License, or (at your option) any later version.
//
//    This library is distributed in the hope that it will be useful,
//    but WITHOUT ANY WARRANTY; without even the implied warranty of
//    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
//    Lesser General Public License for more details.
//
//    You should have received a copy of the GNU Lesser General Public
//    License along with this library; if not, write to the Free Software
//    Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA  02110-1301  USA
//

package Catalano.Imaging.Filters;

import Catalano.Imaging.FastBitmap;
import Catalano.Imaging.IBaseInPlace;

/**
 * Gray World algorithm.
 * @author Diego Catalano
 */
public class GrayWorld implements IBaseInPlace{

    /**
     * Initialize a new instance of the GrayWorld class.
     */
    public GrayWorld() {}

    @Override
    public void applyInPlace(FastBitmap fastBitmap) {
        
        if (fastBitmap.isRGB()){
            int width = fastBitmap.getWidth();
            int height = fastBitmap.getHeight();
            
            double Rmean = 0, Gmean = 0, Bmean = 0;
            for (int i = 0; i < height; i++) {
                for (int j = 0; j < width; j++) {
                    Rmean += fastBitmap.getRed(i, j);
                    Gmean += fastBitmap.getGreen(i, j);
                    Bmean += fastBitmap.getBlue(i, j);
                }
            }
            double size = width * height;
            Rmean /= size;
            Gmean /= size;
            Bmean /= size;
            
            double mean = (Rmean + Gmean + Bmean) / 3;
            
            double kr = mean / Rmean;
            double kg = mean / Gmean;
            double kb = mean / Bmean;
            
            for (int i = 0; i < height; i++) {
                for (int j = 0; j < width; j++) {
                    double r = kr * fastBitmap.getRed(i, j);
                    double g = kg * fastBitmap.getGreen(i, j);
                    double b = kb * fastBitmap.getBlue(i, j);
                    
                    if (r > 255) r = 255;
                    if (g > 255) g = 255;
                    if (b > 255) b = 255;
                    
                    fastBitmap.setRGB(i, j, (int)r, (int)g, (int)b);
                }
            }
        }
        else{
            throw new IllegalArgumentException("Gray World only works in RGB images.");
        }
    }
}