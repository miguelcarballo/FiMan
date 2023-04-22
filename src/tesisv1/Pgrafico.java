package tesisv1;

import java.awt.*;

public class Pgrafico {

    Graphics g;
    int dash;
    int x0, y0, x1, y1;

    public void definirVentana(int x0, int y0, int x1, int y1) {
        this.x0 = x0;
        this.y0 = y0;
        this.x1 = x1;
        this.y1 = y1;
    }

    public boolean dentroVentana(int x, int y) {
        return (x >= x0 && x <= x1 && y >= y0 && y <= y1);
    }

    public void setDash(int d) {
        this.dash = d;
    }

    public Pgrafico() {
        x0 = y0 = 0;
        x1 = y1 = 2000;
    }

    ;

    public void pasarg(Graphics g) {
        this.g = g;
    }

    ;

    public void Circle(int x, int y, int r, boolean fill) {
        int i, j, d, deltaE, deltaSE, n;
        i = 0;
        j = r;
        d = 1 - r;
        deltaE = 3;
        deltaSE = -2 * r + 5;
        if (fill) {
            for (n = x - j; n <= x + j; ++n) {
                g.fillRect(n, y - i, 1, 1);
                g.fillRect(n, y + i, 1, 1);
            }
            for (n = x - i; n <= x + i; ++n) {
                g.fillRect(n, y - j, 1, 1);
                g.fillRect(n, y + j, 1, 1);
            }
        } else {
            g.fillRect(x + i, y + j, 1, 1);
            g.fillRect(x - i, y + j, 1, 1);
            g.fillRect(x + i, y - j, 1, 1);
            g.fillRect(x - i, y - j, 1, 1);
            g.fillRect(x + j, y + i, 1, 1);
            g.fillRect(x + j, y - i, 1, 1);
            g.fillRect(x - j, y + i, 1, 1);
            g.fillRect(x - j, y - i, 1, 1);
        }
        while (j > i) {
            if (d < 0) { //E
                d += deltaE;
                deltaE += 2;
                deltaSE += 2;
                i++;
            } else { // SE
                d += deltaSE;
                deltaE += 2;
                deltaSE += 4;
                i++;
                j--;
            }
            if (fill) {
                for (n = x - j; n <= x + j; ++n) {
                    g.fillRect(n, y - i, 1, 1);
                    g.fillRect(n, y + i, 1, 1);
                }
                for (n = x - i; n <= x + i; ++n) {
                    g.fillRect(n, y - j, 1, 1);
                    g.fillRect(n, y + j, 1, 1);
                }
            } else {
                g.fillRect(x + i, y + j, 1, 1);
                g.fillRect(x - i, y + j, 1, 1);
                g.fillRect(x + i, y - j, 1, 1);
                g.fillRect(x - i, y - j, 1, 1);
                g.fillRect(x + j, y + i, 1, 1);
                g.fillRect(x + j, y - i, 1, 1);
                g.fillRect(x - j, y + i, 1, 1);
                g.fillRect(x - j, y - i, 1, 1);
            }
        }
    }

    public void Line(int x0, int y0, int x1, int y1) {
        int temp, i, j, d, dx, dy, deltaSE, deltaS, deltaE, deltaN, deltaNE;
        int numpunt = 0;
        boolean dibujar = true;
        if (x0 == x1) { // lÃ­nea vertical
            if (y0 > y1) {
                temp = y0;
                y0 = y1;
                y1 = temp;
            }
            for (j = y0; j <= y1; ++j) {
                if (dibujar && dentroVentana(x0, j)) {
                    g.fillRect(x0, j, 1, 1);
                }
                if (dash != 0 && ++numpunt % dash == 0) {
                    dibujar = !dibujar;
                }
            }
        } else if (y0 == y1) { // lÃ­nea horizontal
            if (x0 > x1) {
                temp = x0;
                x0 = x1;
                x1 = temp;
            }
            for (i = x0; i <= x1; ++i) {
                if (dibujar && dentroVentana(i, y0)) {
                    g.fillRect(i, y0, 1, 1);
                }
                if (dash != 0 && ++numpunt % dash == 0) {
                    dibujar = !dibujar;
                }
            }
        } else {
            if (x0 > x1) {  // asegura que la linea sea de izq. a der. para disminuir casos y asegurar simetrÃ­a
                temp = x0;
                x0 = x1;
                x1 = temp;
                temp = y0;
                y0 = y1;
                y1 = temp;
            }
            dx = x1 - x0;
            dy = y1 - y0;
            i = x0;
            j = y0;
            d = 2 * dy - dx;
            deltaE = 2 * dy;
            deltaS = -2 * dx;
            deltaSE = deltaS + deltaE;
            deltaN = 2 * dx;
            deltaNE = deltaN + deltaE;
            if (dentroVentana(i, j)) {
                g.fillRect(i, j, 1, 1);
            }
            if (y0 < y1) // la linea es hacia abajo
            {
                if (dx >= dy) // pendiente <= 1
                {
                    while (i < x1) {
                        if (d <= 0) { //E
                            d += deltaE;
                            i++;
                        } else { // SE
                            d += deltaSE;
                            i++;
                            j++;
                        }
                        if (dibujar && dentroVentana(i, j)) {
                            g.fillRect(i, j, 1, 1);
                        }
                        if (dash != 0 && ++numpunt % dash == 0) {
                            dibujar = !dibujar;
                        }

                    }
                } else // pendiente > 1
                {
                    while (i < x1) {
                        if (d <= 0) { // SE
                            d += deltaSE;
                            i++;
                            j++;
                        } else {	// S
                            d = d + deltaS;
                            j++;
                        }
                        if (dibujar && dentroVentana(i, j)) {
                            g.fillRect(i, j, 1, 1);
                        }
                        if (dash != 0 && ++numpunt % dash == 0) {
                            dibujar = !dibujar;
                        }
                    }
                }
            } else // la linea es hacia arriba
            if (dx >= -dy) // pendiente <=1
            {
                while (i < x1) {
                    if (d >= 0) { // E
                        d += deltaE;
                        i++;
                    } else { // NE
                        d += deltaNE;
                        i++;
                        j--;
                    }
                    if (dibujar && dentroVentana(i, j)) {
                        g.fillRect(i, j, 1, 1);
                    }
                    if (dash != 0 && ++numpunt % dash == 0) {
                        dibujar = !dibujar;
                    }
                }
            } else {
                while (i < x1) { // pendiente > 1
                    if (d >= 0) { // NE
                        d += deltaNE;
                        i++;
                        j--;
                    } else { // N
                        d += deltaN;
                        j--;
                    }
                    if (dibujar && dentroVentana(i, j)) {
                        g.fillRect(i, j, 1, 1);
                    }
                    if (dash != 0 && ++numpunt % dash == 0) {
                        dibujar = !dibujar;
                    }
                }
            }
        }
    }

    public void Box(int x0, int y0, int x1, int y1, boolean fill) {
        int temp, i, j;
        if (x0 > x1) {
            temp = x0;
            x0 = x1;
            x1 = temp;
            temp = y0;
            y0 = y1;
            y1 = temp;
        }
        if (fill) {
            for (i = x0; i <= x1; ++i) {
                for (j = y0; j <= y1; ++j) {
                    g.fillRect(i, j, 1, 1);
                }
            }
        } else {
            for (i = x0; i < x1; ++i) {
                g.fillRect(i, y0, 1, 1);
                g.fillRect(i, y1, 1, 1);
            }
            for (j = y0; j <= y1; ++j) {
                g.fillRect(x0, j, 1, 1);
                g.fillRect(x1, j, 1, 1);
            }
        }
    }
}
