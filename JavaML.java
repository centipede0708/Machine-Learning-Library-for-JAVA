import java.awt.*;
import java.awt.geom.Ellipse2D;
import java.util.*;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class JavaML {
    // RANDOM POINT GENERATOR
    //////////////////////////////////////////////////////////////////////////////////////////////////////
    /// /////////////////////////////////////////////////////////////////////////////////////////////////////
    /// ////////////////////////////////////////////////////////////////////////////////////////////////////////
    public ArrayList<ArrayList<float[]>> RandomPointGenerator(int outerSize, int innerSize, int dimensions,
            float minRange, float maxRange) {
        Random random = new Random();
        ArrayList<ArrayList<float[]>> points = new ArrayList<>();

        for (int i = 0; i < outerSize; i++) {
            ArrayList<float[]> innerList = new ArrayList<>();
            for (int j = 0; j < innerSize; j++) {
                float[] point = new float[dimensions];
                for (int k = 0; k < dimensions; k++) {
                    point[k] = minRange + random.nextFloat() * (maxRange - minRange);
                }
                innerList.add(point);
            }
            points.add(innerList);
        }
        return points;
    }

    public static void printFloatArrayList(ArrayList<float[]> list) {
        for (int i = 0; i < list.size(); i++) {
            float[] arr = list.get(i);
            System.out.print("Row " + (i + 1) + ": ");
            System.out.println(Arrays.toString(arr));
        }
    }

    // PLOT
    ////////////////////////////////////////////////////////////////////////////////////////////////////////
    /// /////////////////////////////////////////////////////////////////////////////////////////////////////
    /// ///////////////////////////////////////////////////////////////////////////////////////////////////
    /// /////////////////////////////////////////////////////////////////////////////////////////////////
    // 2D plot
    public static void plot(ArrayList<ArrayList<float[]>> result) {
        ArrayList<Integer> n = new ArrayList<>();
        n.add(0);
        // window
        JFrame window = new JFrame();
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setResizable(false);
        ClusterPanel gp = new ClusterPanel(result);
        window.add(gp);
        window.pack();
        window.setLocationRelativeTo(null);
        window.setVisible(true);
    }

    private static void drawPoints(Graphics2D g2, ArrayList<Float> input, ArrayList<Float> output,
            ArrayList<Integer> colorChangeIDX, float minIP, float minOP, float rangeIP, float rangeOP, int marginX,
            int marginY, int graphWidth, int graphHeight, int ScreenHeight, Color c) {
        g2.setColor(c);
        int size = colorChangeIDX.size();
        int colorIDX = colorChangeIDX.getFirst();
        for (int i = 0; i < input.size(); i++) {
            if (colorChangeIDX.size() != 0) {
                colorIDX = colorChangeIDX.getFirst();
            }
            if (i == colorIDX) {
                switch (size) {
                    case 1:
                        g2.setColor(Color.red);
                        break;
                    case 2:
                        g2.setColor(Color.GREEN);
                        break;
                    case 3:
                        g2.setColor(Color.ORANGE);
                        break;
                    case 4:
                        g2.setColor(Color.PINK);
                        break;
                }
                colorChangeIDX.removeFirst();
                size--;
            }
            float normalizedX = (input.get(i) - minIP) / rangeIP;
            float normalizedY = (output.get(i) - minOP) / rangeOP;

            int x = (int) (marginX + normalizedX * graphWidth);
            int y = (int) (ScreenHeight - marginY - normalizedY * graphHeight);

            float radius = 5;
            Ellipse2D.Float circle = new Ellipse2D.Float(x - radius, y - radius, 2 * radius, 2 * radius);
            g2.fill(circle);
        }
    }

    // 3D plot
    public static void Plot3D(ArrayList<ArrayList<float[]>> dataset) {
        ArrayList<float[]> flattenedData = new ArrayList<>();
        for (ArrayList<float[]> group : dataset) {
            flattenedData.addAll(group);
        }

        JFrame frame = new JFrame("Plot 3D");
        Plot3D plot = new Plot3D(flattenedData);
        frame.setBackground(Color.BLACK);
        frame.add(plot);
        frame.setSize(600, 600);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }

    private static class Plot3D extends JPanel {
        private final ArrayList<float[]> points;
        private static final int TICK_SPACING = 50;
        private static final int AXIS_LENGTH = 200;

        public Plot3D(ArrayList<float[]> points) {
            this.points = points;
        }

        @Override
        protected void paintComponent(Graphics g) {
            setBackground(new Color(183, 196, 207)); // B7C4CF in RGB
            super.paintComponent(g);
            Graphics2D g2d = (Graphics2D) g;
            g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

            int width = getWidth();
            int height = getHeight();
            g2d.translate(width / 2, height / 2);

            drawPlane(g2d);
            drawAxis(g2d);

            g2d.setColor(Color.ORANGE);
            for (float[] p : points) {
                if (p.length >= 3) {
                    int x = (int) (p[0] - p[1]);
                    int y = (int) (-p[2] + (p[0] + p[1]) / 2);
                    g2d.fillOval(x - 2, y - 2, 4, 4);
                }
            }
        }

        private void drawPlane(Graphics2D g2d) {
            g2d.setColor(new Color(183, 196, 207, 50));
            int gridSize = AXIS_LENGTH, step = 20;
            for (int i = -gridSize; i <= gridSize; i += step) {
                g2d.drawLine(i, -gridSize, i, gridSize);
                g2d.drawLine(-gridSize, i, gridSize, i);
            }
        }

        private void drawAxis(Graphics2D g2d) {
            g2d.setStroke(new BasicStroke(2));

            // X-axis
            drawArrow(g2d, -AXIS_LENGTH, 0, AXIS_LENGTH, 0, Color.RED, "X");
            // Y-axis
            drawArrow(g2d, 0, -AXIS_LENGTH, 0, AXIS_LENGTH, Color.GREEN, "Y");
            // Z-axis
            drawArrow(g2d, -AXIS_LENGTH / 2, AXIS_LENGTH / 2, AXIS_LENGTH / 2, -AXIS_LENGTH / 2, Color.BLUE, "Z");
        }

        private void drawArrow(Graphics2D g2d, int x1, int y1, int x2, int y2, Color color, String axis) {
            g2d.setColor(color);
            g2d.drawLine(x1, y1, x2, y2);
            drawArrowHead(g2d, x1, y1, x2, y2);
            drawArrowHead(g2d, x2, y2, x1, y1);
            drawTicks(g2d, x1, y1, x2, y2, axis);
        }

        private void drawArrowHead(Graphics2D g2d, int x1, int y1, int x2, int y2) {
            int arrowSize = 8;
            double angle = Math.atan2(y2 - y1, x2 - x1);
            int xArrow1 = (int) (x2 - arrowSize * Math.cos(angle - Math.PI / 6));
            int yArrow1 = (int) (y2 - arrowSize * Math.sin(angle - Math.PI / 6));
            int xArrow2 = (int) (x2 - arrowSize * Math.cos(angle + Math.PI / 6));
            int yArrow2 = (int) (y2 - arrowSize * Math.sin(angle + Math.PI / 6));

            g2d.drawLine(x2, y2, xArrow1, yArrow1);
            g2d.drawLine(x2, y2, xArrow2, yArrow2);
        }

        private void drawTicks(Graphics2D g2d, int x1, int y1, int x2, int y2, String axis) {
            g2d.setStroke(new BasicStroke(1));
            g2d.setColor(Color.black);
            int tickLength = 5;
            int labelOffset = 15;
            int numTicks = 2 * AXIS_LENGTH / TICK_SPACING;

            for (int i = 1; i <= numTicks; i++) {
                int tx = x1 + i * (x2 - x1) / numTicks;
                int ty = y1 + i * (y2 - y1) / numTicks;
                g2d.drawLine(tx, ty - tickLength, tx, ty + tickLength);
                g2d.drawString(String.valueOf(i * TICK_SPACING), tx - labelOffset, ty + labelOffset);
            }

            // Axis Label
            g2d.setFont(new Font("Arial", Font.BOLD, 14));

            g2d.drawString(axis, x2 + labelOffset, y2 - labelOffset);
        }
    }

    ////////////// REGRESSION CURVES
    /// ///////////////////////////////////////////////////////////////////////////////////////////////////
    /// ////////////////////////////////////////////////////////////////////////////////////////////////////
    /// ///////////////////////////////////////////////////////////////////////////////////////////////////
    /// ////////////////////////////////////////////////////////////////////////////////////////////////////
    public static void plotRegression(ArrayList<ArrayList<float[]>> dataset, ArrayList<Float> coeff) {
        ArrayList<Float> input = new ArrayList<>();
        ArrayList<Float> output = new ArrayList<>();

        // Flatten dataset into separate input and output lists
        for (ArrayList<float[]> group : dataset) {
            for (float[] point : group) {
                if (point.length >= 2) { // Ensure (x, y) format
                    input.add(point[0]); // X value
                    output.add(point[1]); // Y value
                }
            }
        }

        // Window setup
        JFrame window = new JFrame();
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setResizable(false);
        videopanel gp = new videopanel(input, output, coeff);
        window.add(gp);
        window.pack();
        window.setLocationRelativeTo(null);
        window.setVisible(true);
    }

    private static class videopanel extends JPanel {
        final int ScreenWidth = 1280;
        final int ScreenHeight = 720;
        ArrayList<Float> input;
        ArrayList<Float> output;
        ArrayList<Float> coeff;

        public videopanel(ArrayList<Float> input, ArrayList<Float> output, ArrayList<Float> coeff) {
            this.setPreferredSize(new Dimension(ScreenWidth, ScreenHeight));
            this.setBackground(Color.black);
            this.setFocusable(true);
            this.setDoubleBuffered(true);
            this.input = input;
            this.output = output;
            this.coeff = coeff;
        }

        public void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D g2 = (Graphics2D) g;
            g2.setColor(Color.white);

            // Define margins
            int marginX = 100;
            int marginY = 50;
            int graphWidth = ScreenWidth - 2 * marginX;
            int graphHeight = ScreenHeight - 2 * marginY;

            // Determine min and max values to handle negative numbers
            float minIP = Collections.min(input);
            float maxIP = Collections.max(input);
            float minOP = Collections.min(output);
            float maxOP = Collections.max(output);

            // Adjust min values to start from zero if they are greater than zero
            minIP = (minIP > 0) ? 0 : minIP;
            minOP = (minOP > 0) ? 0 : minOP;

            float rangeIP = maxIP - minIP;
            float rangeOP = maxOP - minOP;

            // Origin position
            int originX = (int) (marginX + (-minIP / rangeIP) * graphWidth);
            int originY = (int) (ScreenHeight - marginY - (-minOP / rangeOP) * graphHeight);

            // Draw axes
            g2.drawString("0", originX - 15, originY + 15);
            g2.drawLine(marginX, originY, ScreenWidth - marginX, originY); // X-axis
            g2.drawLine(originX, ScreenHeight - marginY, originX, marginY); // Y-axis

            // Add tick marks and labels
            drawTicks(g2, minIP, maxIP, minOP, maxOP, originX, originY, marginX, marginY, graphWidth, graphHeight);

            // Draw arrowheads
            drawArrowHead(g2, ScreenWidth - marginX, originY, 1, 0); // X-axis right
            drawArrowHead(g2, marginX, originY, -1, 0); // X-axis left
            drawArrowHead(g2, originX, marginY, 0, -1); // Y-axis up
            drawArrowHead(g2, originX, ScreenHeight - marginY, 0, 1); // Y-axis down

            // Plot data points
            drawPoints(g2, input, output, minIP, minOP, rangeIP, rangeOP, marginX, marginY, graphWidth, graphHeight,
                    ScreenHeight, Color.BLUE);

            // Draw polynomial regression curve
            drawRegressionCurve(g2, coeff, minIP, maxIP, minOP, rangeIP, rangeOP, marginX, marginY, graphWidth,
                    graphHeight, ScreenHeight);
        }

        private void drawPoints(Graphics2D g2, ArrayList<Float> input, ArrayList<Float> output,
                float minIP, float minOP, float rangeIP, float rangeOP,
                int marginX, int marginY, int graphWidth, int graphHeight, int screenHeight, Color color) {
            g2.setColor(color);
            int pointSize = 6;

            for (int i = 0; i < input.size(); i++) {
                float x = input.get(i);
                float y = output.get(i);

                // Convert (x, y) from data space to screen space
                int screenX = marginX + (int) ((x - minIP) / rangeIP * graphWidth);
                int screenY = screenHeight - marginY - (int) ((y - minOP) / rangeOP * graphHeight);

                // Ensure the points don't go out of bounds
                screenX = Math.max(marginX, Math.min(screenX, ScreenWidth - marginX));
                screenY = Math.max(marginY, Math.min(screenY, screenHeight - marginY));

                // Draw point
                g2.fillOval(screenX - pointSize / 2, screenY - pointSize / 2, pointSize, pointSize);
            }
        }

        private void drawTicks(Graphics2D g2, float minIP, float maxIP, float minOP, float maxOP,
                int originX, int originY, int marginX, int marginY, int graphWidth, int graphHeight) {
            g2.setColor(Color.WHITE);
            int numTicks = 10; // Number of tick marks

            // X-axis ticks
            for (int i = 0; i <= numTicks; i++) {
                float value = minIP + (i / (float) numTicks) * (maxIP - minIP);
                int x = marginX + (i * graphWidth / numTicks);
                g2.drawLine(x, originY - 5, x, originY + 5);
                g2.drawString(String.format("%.2f", value), x - 15, originY + 20);
            }

            // Y-axis ticks
            for (int i = 0; i <= numTicks; i++) {
                float value = minOP + (i / (float) numTicks) * (maxOP - minOP);
                int y = ScreenHeight - marginY - (i * graphHeight / numTicks);
                g2.drawLine(originX - 5, y, originX + 5, y);
                g2.drawString(String.format("%.2f", value), originX - 40, y + 5);
            }
        }

        private void drawRegressionCurve(Graphics2D g2, ArrayList<Float> coeff,
                float minIP, float maxIP, float minOP, float rangeIP, float rangeOP,
                int marginX, int marginY, int graphWidth, int graphHeight, int screenHeight) {
            g2.setColor(Color.MAGENTA);
            int prevX = -1, prevY = -1;
            int numPoints = 500; // Increase for smoother curve

            for (int i = 0; i <= numPoints; i++) {
                float x = minIP + (i / (float) numPoints) * (maxIP - minIP);
                float y = evaluatePolynomial(coeff, x);

                int screenX = marginX + (int) ((x - minIP) / rangeIP * graphWidth);
                int screenY = screenHeight - marginY - (int) ((y - minOP) / rangeOP * graphHeight);

                if (prevX != -1 && prevY != -1) {
                    g2.drawLine(prevX, prevY, screenX, screenY);
                }

                prevX = screenX;
                prevY = screenY;
            }
        }

        private float evaluatePolynomial(ArrayList<Float> coeff, float x) {
            float y = 0;
            for (int d = 0; d < coeff.size(); d++) {
                y += coeff.get(d) * Math.pow(x, d);
            }
            return y;
        }

        private void drawArrowHead(Graphics2D g2, int x, int y, int dx, int dy) {
            int arrowSize = 10;
            int[] xPoints = { x, x - arrowSize * dy - arrowSize * dx, x + arrowSize * dy - arrowSize * dx };
            int[] yPoints = { y, y - arrowSize * dx - arrowSize * dy, y + arrowSize * dx - arrowSize * dy };
            g2.fillPolygon(xPoints, yPoints, 3);
        }
    }

    // multi degree regression
    public static ArrayList<Float> TrainPolynomialRegressionModel(ArrayList<ArrayList<float[]>> dataset, int degree,
            float learningRate, int iterations) {
        ArrayList<Float> ans = new ArrayList<>();
        ArrayList<Float> input = new ArrayList<>();
        ArrayList<Float> output = new ArrayList<>();

        // Flatten dataset into separate input and output lists
        for (ArrayList<float[]> group : dataset) {
            for (float[] point : group) {
                if (point.length >= 2) { // Ensure (x, y) format
                    input.add(point[0]); // X value
                    output.add(point[1]); // Y value
                }
            }
        }

        int n = input.size();
        if (n == 0) {
            System.out.println("Error: Empty dataset");
            return ans;
        }

        // Initialize coefficients (w0, w1, w2, ..., w_degree)
        float[] coefficients = new float[degree + 1];

        // Gradient Descent
        for (int i = 0; i < iterations; i++) {
            float[] gradients = new float[degree + 1];

            for (int j = 0; j < n; j++) {
                float x = input.get(j);
                float y = output.get(j);

                // Compute polynomial prediction
                float prediction = 0;
                for (int d = 0; d <= degree; d++) {
                    prediction += coefficients[d] * Math.pow(x, d);
                }
                float error = prediction - y;

                // Compute gradients for each coefficient
                for (int d = 0; d <= degree; d++) {
                    gradients[d] += (error * Math.pow(x, d)) / n;
                }
            }

            // Update coefficients using gradient descent
            for (int d = 0; d <= degree; d++) {
                coefficients[d] -= learningRate * gradients[d];
            }

            // Check for NaN values
            for (float coef : coefficients) {
                if (Float.isNaN(coef)) {
                    System.out.println("Training diverged: NaN detected. Try reducing the learning rate.");
                    return ans;
                }
            }
        }

        // Print trained polynomial equation
        System.out.print("Trained Model: y = ");
        for (int d = 0; d <= degree; d++) {
            System.out.print(coefficients[d] + "x^" + d);
            if (d != degree)
                System.out.print(" + ");
        }
        System.out.println();

        // Store coefficients in result list
        for (float coef : coefficients) {
            ans.add(coef);
        }

        return ans;
    }
    ///////////// CLUSTERING ALGORITHMS
    /// /////////////////////////////////////////////////////////////////////////////////////////////////////
    /// ////////////////////////////////////////////////////////////////////////////////////////////////////////
    /// ///////////////////////////////////////////////////////////////////////////////////////////////////////
    /// //////////////////////////////////////////////////////////////////////////////////////////////////////

    // knn++

    public static ArrayList<ArrayList<float[]>> Clustering(ArrayList<ArrayList<float[]>> inputPoints,
            int numberOfClusters) {
        ArrayList<ArrayList<float[]>> clusters = new ArrayList<>();
        ArrayList<float[]> centroids = new ArrayList<>();
        Random random = new Random();

        // Flatten input points into a single list
        ArrayList<float[]> allPoints = new ArrayList<>();
        for (ArrayList<float[]> group : inputPoints) {
            allPoints.addAll(group);
        }

        // Initialize centroids randomly
        for (int i = 0; i < numberOfClusters; i++) {
            int randIndex = random.nextInt(allPoints.size());
            centroids.add(allPoints.get(randIndex));
            clusters.add(new ArrayList<>());
        }

        boolean hasChanged;
        final double EPSILON = 0.0001;

        do {
            // Clear previous clusters
            for (ArrayList<float[]> cluster : clusters) {
                cluster.clear();
            }

            // Assign each point to the nearest centroid
            for (float[] point : allPoints) {
                int nearestCluster = getNearestCluster(point, centroids);
                clusters.get(nearestCluster).add(point);
            }

            // Update centroids
            hasChanged = false;
            for (int i = 0; i < numberOfClusters; i++) {
                if (!clusters.get(i).isEmpty()) {
                    float[] newCentroid = computeMean(clusters.get(i));

                    // Update centroid if it significantly changes
                    if (distance(newCentroid, centroids.get(i)) > EPSILON) {
                        centroids.set(i, newCentroid);
                        hasChanged = true;
                    }
                } else {
                    // Reinitialize empty cluster centroid
                    int randIndex = random.nextInt(allPoints.size());
                    centroids.set(i, allPoints.get(randIndex));
                    hasChanged = true;
                }
            }
        } while (hasChanged);

        return clusters;
    }

    public static void printCluster(ArrayList<ArrayList<float[]>> result) {
        for (int i = 0; i < result.size(); i++) {
            System.out.print("Cluster " + (i + 1) + ": ");
            for (float[] point : result.get(i)) {
                System.out.print("(" + point[0] + ", " + point[1] + ") ");
            }
            System.out.println();
        }
    }

    private static int getNearestCluster(float[] point, ArrayList<float[]> centroids) {
        int nearestIndex = 0;
        double minDistance = distance(point, centroids.get(0));

        for (int i = 1; i < centroids.size(); i++) {
            double dist = distance(point, centroids.get(i));
            if (dist < minDistance) {
                minDistance = dist;
                nearestIndex = i;
            }
        }
        return nearestIndex;
    }

    private static float[] computeMean(ArrayList<float[]> cluster) {
        int dimensions = cluster.get(0).length;
        float[] mean = new float[dimensions];

        for (float[] point : cluster) {
            for (int i = 0; i < dimensions; i++) {
                mean[i] += point[i];
            }
        }

        for (int i = 0; i < dimensions; i++) {
            mean[i] /= cluster.size();
        }

        return mean;
    }

    private static double distance(float[] p1, float[] p2) {
        double sum = 0;
        for (int i = 0; i < p1.length; i++) {
            sum += Math.pow(p1[i] - p2[i], 2);
        }
        return Math.sqrt(sum);
    }

    private static class ClusterPanel extends JPanel {
        final int ScreenWidth = 1280;
        final int ScreenHeight = 720;
        ArrayList<ArrayList<float[]>> result;

        public ClusterPanel(ArrayList<ArrayList<float[]>> result) {
            this.setPreferredSize(new Dimension(ScreenWidth, ScreenHeight));
            this.setBackground(Color.black);
            this.setFocusable(true);
            this.setDoubleBuffered(true);
            this.result = result;
        }

        public void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D g2 = (Graphics2D) g;
            g2.setColor(Color.white);

            int marginX = 100;
            int marginY = 50;
            int graphWidth = ScreenWidth - 2 * marginX;
            int graphHeight = ScreenHeight - 2 * marginY;

            float minX = Float.MAX_VALUE, maxX = Float.MIN_VALUE;
            float minY = Float.MAX_VALUE, maxY = Float.MIN_VALUE;

            for (ArrayList<float[]> cluster : result) {
                for (float[] point : cluster) {
                    minX = Math.min(minX, point[0]);
                    maxX = Math.max(maxX, point[0]);
                    minY = Math.min(minY, point[1]);
                    maxY = Math.max(maxY, point[1]);
                }
            }

            minX = (minX > 0) ? 0 : minX;
            minY = (minY > 0) ? 0 : minY;

            float rangeX = maxX - minX;
            float rangeY = maxY - minY;

            int originX = (int) (marginX + (-minX / rangeX) * graphWidth);
            int originY = (int) (ScreenHeight - marginY - (-minY / rangeY) * graphHeight);

            g2.drawString("0", originX - 15, originY + 15);

            g2.drawLine(marginX, originY, ScreenWidth - marginX, originY);
            g2.drawLine(originX, ScreenHeight - marginY, originX, marginY);

            int numDivisionsX = 10;
            int numDivisionsY = 10;
            int xSpacing = graphWidth / numDivisionsX;
            int ySpacing = graphHeight / numDivisionsY;

            for (int i = 0; i <= numDivisionsX; i++) {
                float value = minX + (rangeX / numDivisionsX) * i;
                int x = marginX + i * xSpacing;
                g2.drawLine(x, originY - 5, x, originY + 5);
                g2.drawString(String.format("%.2f", value), x - 10, originY + 20);
            }

            for (int i = 0; i <= numDivisionsY; i++) {
                float value = minY + (rangeY / numDivisionsY) * i;
                int y = ScreenHeight - marginY - i * ySpacing;
                g2.drawLine(originX - 5, y, originX + 5, y);
                g2.drawString(String.format("%.2f", value), originX - 40, y + 5);
            }

            drawArrowHead(g2, ScreenWidth - marginX, originY, 1, 0);
            if (minX < 0) {
                drawArrowHead(g2, marginX, originY, -1, 0);
            }

            drawArrowHead(g2, originX, marginY, 0, -1);
            if (minY < 0) {
                drawArrowHead(g2, originX, ScreenHeight - marginY, 0, 1);
            }

            Color[] colors = { Color.RED, Color.GREEN, Color.BLUE, Color.YELLOW, Color.CYAN, Color.MAGENTA };
            int colorIdx = 0;

            for (ArrayList<float[]> cluster : result) {
                g2.setColor(colors[colorIdx % colors.length]);
                for (float[] point : cluster) {
                    int x = marginX + (int) ((point[0] - minX) / rangeX * graphWidth);
                    int y = ScreenHeight - marginY - (int) ((point[1] - minY) / rangeY * graphHeight);
                    g2.fillOval(x - 3, y - 3, 6, 6);
                }
                colorIdx++;
            }
        }

        private void drawArrowHead(Graphics2D g2, int x, int y, int dx, int dy) {
            int arrowSize = 10;

            int[] xPoints = { x, x - arrowSize * dy - arrowSize * dx, x + arrowSize * dy - arrowSize * dx };
            int[] yPoints = { y, y - arrowSize * dx - arrowSize * dy, y + arrowSize * dx - arrowSize * dy };

            g2.fillPolygon(xPoints, yPoints, 3);
        }
    }

    // ///////////// REINFORCEMENT LEARNING
    //////////////////////////////////////////////////////////////////////////////////////////////////////////
    /// ///////////////////////////////////////////////////////////////////////////////////////////////////
    /// ////////////////////////////////////////////////////////////////////////////////////////////////////
    /// /////////////////////////////////////////////////////////////////////////////////////////////////////

    /////////////////// DIMENTIONALITY INCREASE AND DECREASE
    /// ///////////////////////////////////////////////////////////////////////////////////////////////////////////
    /// /////////////////////////////////////////////////////////////////////////////////////////////////////////////
    /// //////////////////////////////////////////////////////////////////////////////////////////////////////////////
    /// ///////////////////////////////////////////////////////////////////////////////////////////////////////////////
    /// //////////////////////////////////////////////////////////////////////////////////////////////////////////////
    /// DATA REDUCTION

    private ArrayList<ArrayList<float[]>> projectDataset(ArrayList<ArrayList<float[]>> dataset, float[][] ldaComponents,
            int numFeatures) {
        ArrayList<ArrayList<float[]>> transformedDataset = new ArrayList<>();
        for (ArrayList<float[]> classData : dataset) {
            ArrayList<float[]> transformedClass = new ArrayList<>();
            for (float[] sample : classData) {
                float[] projected = new float[ldaComponents[0].length];
                for (int i = 0; i < ldaComponents[0].length; i++) {
                    for (int j = 0; j < numFeatures; j++) {
                        projected[i] += ldaComponents[j][i] * sample[j];
                    }
                }
                transformedClass.add(projected);
            }
            transformedDataset.add(transformedClass);
        }
        return transformedDataset;
    }

    // pca
    public ArrayList<ArrayList<float[]>> PCA(ArrayList<ArrayList<float[]>> dataset, int numComponents) {
        int numSamples = dataset.stream().mapToInt(List::size).sum();
        int numFeatures = dataset.get(0).get(0).length;

        // Step 1: Compute Mean Vector
        float[] meanVector = computeMeanVector(dataset, numSamples, numFeatures);

        // Step 2: Center the Dataset
        ArrayList<ArrayList<float[]>> centeredData = centerDataset(dataset, meanVector);

        // Step 3: Compute Covariance Matrix
        float[][] covarianceMatrix = computeCovarianceMatrix(centeredData, numSamples, numFeatures);

        // Step 4: Compute Principal Components using SVD
        float[][] principalComponents = computeSVD(covarianceMatrix, numFeatures, numComponents);

        // Step 5: Transform the Dataset
        return projectDataset(centeredData, principalComponents, numComponents);
    }

    private float[] computeMeanVector(ArrayList<ArrayList<float[]>> dataset, int numSamples, int numFeatures) {
        float[] meanVector = new float[numFeatures];
        for (ArrayList<float[]> classSamples : dataset) {
            for (float[] sample : classSamples) {
                for (int i = 0; i < numFeatures; i++) {
                    meanVector[i] += sample[i];
                }
            }
        }
        for (int i = 0; i < numFeatures; i++) {
            meanVector[i] /= numSamples;
        }
        return meanVector;
    }

    private ArrayList<ArrayList<float[]>> centerDataset(ArrayList<ArrayList<float[]>> dataset, float[] meanVector) {
        ArrayList<ArrayList<float[]>> centeredData = new ArrayList<>();
        for (ArrayList<float[]> classSamples : dataset) {
            ArrayList<float[]> centeredClass = new ArrayList<>();
            for (float[] sample : classSamples) {
                float[] centeredSample = new float[sample.length];
                for (int i = 0; i < sample.length; i++) {
                    centeredSample[i] = sample[i] - meanVector[i];
                }
                centeredClass.add(centeredSample);
            }
            centeredData.add(centeredClass);
        }
        return centeredData;
    }

    private float[][] computeCovarianceMatrix(ArrayList<ArrayList<float[]>> dataset, int numSamples, int numFeatures) {
        float[][] covarianceMatrix = new float[numFeatures][numFeatures];

        for (ArrayList<float[]> classSamples : dataset) {
            for (float[] sample : classSamples) {
                for (int i = 0; i < numFeatures; i++) {
                    for (int j = 0; j < numFeatures; j++) {
                        covarianceMatrix[i][j] += sample[i] * sample[j];
                    }
                }
            }
        }

        for (int i = 0; i < numFeatures; i++) {
            for (int j = 0; j < numFeatures; j++) {
                covarianceMatrix[i][j] /= (numSamples - 1);
            }
        }

        return covarianceMatrix;
    }

    private float[][] computeSVD(float[][] matrix, int numFeatures, int numComponents) {
        // Implementing SVD manually using Jacobi's method
        float[][] u = new float[numFeatures][numFeatures];
        float[][] s = new float[numFeatures][numFeatures];
        float[][] v = new float[numFeatures][numFeatures];

        svdDecomposition(matrix, u, s, v, numFeatures);

        float[][] principalComponents = new float[numFeatures][numComponents];
        for (int i = 0; i < numFeatures; i++) {
            for (int j = 0; j < numComponents; j++) {
                principalComponents[i][j] = v[i][j];
            }
        }

        return principalComponents;
    }

    private void svdDecomposition(float[][] matrix, float[][] u, float[][] s, float[][] v, int n) {
        // Basic Jacobi eigenvalue algorithm for SVD
        for (int i = 0; i < n; i++) {
            u[i][i] = 1.0f;
            v[i][i] = 1.0f;
        }

        float[] eigenvalues = new float[n];
        for (int i = 0; i < n; i++) {
            eigenvalues[i] = matrix[i][i];
            s[i][i] = matrix[i][i];
        }

        for (int i = 0; i < n - 1; i++) {
            for (int j = i + 1; j < n; j++) {
                float angle = (float) Math.atan2(2 * matrix[i][j], matrix[i][i] - matrix[j][j]) / 2;
                float cos = (float) Math.cos(angle);
                float sin = (float) Math.sin(angle);

                float temp1 = cos * matrix[i][i] + sin * matrix[j][i];
                float temp2 = cos * matrix[j][j] - sin * matrix[j][i];
                matrix[i][i] = temp1;
                matrix[j][j] = temp2;

                for (int k = 0; k < n; k++) {
                    float t1 = cos * u[k][i] + sin * u[k][j];
                    float t2 = cos * u[k][j] - sin * u[k][i];
                    u[k][i] = t1;
                    u[k][j] = t2;
                }

                for (int k = 0; k < n; k++) {
                    float t1 = cos * v[k][i] + sin * v[k][j];
                    float t2 = cos * v[k][j] - sin * v[k][i];
                    v[k][i] = t1;
                    v[k][j] = t2;
                }
            }
        }
    }

    // umap
    public ArrayList<ArrayList<float[]>> UMAP(ArrayList<ArrayList<float[]>> dataset, int numComponents,
            int nNeighbors) {
        int numClasses = dataset.size();
        int numSamples = dataset.stream().mapToInt(List::size).sum();
        int numFeatures = dataset.get(0).get(0).length;

        // Step 1: Flatten dataset into single list for processing
        List<float[]> allSamples = flattenDataset(dataset);

        // Step 2: Compute k-Nearest Neighbors (KNN Graph)
        int[][] knnGraph = computeKNNGraph(allSamples, nNeighbors, numFeatures);

        // Step 3: Construct Graph Weights
        float[][] edgeWeights = computeGraphWeights(knnGraph, allSamples, numSamples, nNeighbors);

        // Step 4: Optimize Low-Dimensional Embedding
        float[][] lowDimEmbedding = optimizeEmbedding(edgeWeights, numSamples, numComponents);

        // Step 5: Reconstruct Dataset in Original Structure
        return reconstructDataset(dataset, lowDimEmbedding, numComponents);
    }

    private List<float[]> flattenDataset(ArrayList<ArrayList<float[]>> dataset) {
        List<float[]> allSamples = new ArrayList<>();
        for (ArrayList<float[]> classSamples : dataset) {
            allSamples.addAll(classSamples);
        }
        return allSamples;
    }

    private int[][] computeKNNGraph(List<float[]> allSamples, int nNeighbors, int numFeatures) {
        int numSamples = allSamples.size();
        int[][] knnGraph = new int[numSamples][nNeighbors];

        for (int i = 0; i < numSamples; i++) {
            PriorityQueue<int[]> pq = new PriorityQueue<>(Comparator.comparingDouble(a -> a[1]));
            for (int j = 0; j < numSamples; j++) {
                if (i != j) {
                    float distance = euclideanDistance(allSamples.get(i), allSamples.get(j));
                    pq.offer(new int[] { j, (int) distance });
                    if (pq.size() > nNeighbors) {
                        pq.poll();
                    }
                }
            }
            for (int k = 0; k < nNeighbors; k++) {
                knnGraph[i][k] = pq.poll()[0];
            }
        }
        return knnGraph;
    }

    private float euclideanDistance(float[] a, float[] b) {
        float sum = 0;
        for (int i = 0; i < a.length; i++) {
            float diff = a[i] - b[i];
            sum += diff * diff;
        }
        return (float) Math.sqrt(sum);
    }

    private float[][] computeGraphWeights(int[][] knnGraph, List<float[]> allSamples, int numSamples, int nNeighbors) {
        float[][] edgeWeights = new float[numSamples][numSamples];

        for (int i = 0; i < numSamples; i++) {
            for (int j = 0; j < nNeighbors; j++) {
                int neighborIndex = knnGraph[i][j];
                float distance = euclideanDistance(allSamples.get(i), allSamples.get(neighborIndex));
                edgeWeights[i][neighborIndex] = (float) Math.exp(-distance);
            }
        }
        return edgeWeights;
    }

    private float[][] optimizeEmbedding(float[][] edgeWeights, int numSamples, int numComponents) {
        float[][] lowDimEmbedding = new float[numSamples][numComponents];
        Random random = new Random();

        // Initialize embeddings randomly
        for (int i = 0; i < numSamples; i++) {
            for (int j = 0; j < numComponents; j++) {
                lowDimEmbedding[i][j] = random.nextFloat() * 2 - 1;
            }
        }

        // Basic gradient descent optimization (placeholder)
        for (int iter = 0; iter < 100; iter++) {
            for (int i = 0; i < numSamples; i++) {
                for (int j = 0; j < numComponents; j++) {
                    lowDimEmbedding[i][j] += 0.01f * (float) Math.random() - 0.005f;
                }
            }
        }

        return lowDimEmbedding;
    }

    private ArrayList<ArrayList<float[]>> reconstructDataset(ArrayList<ArrayList<float[]>> dataset,
            float[][] lowDimEmbedding, int numComponents) {
        ArrayList<ArrayList<float[]>> transformedDataset = new ArrayList<>();
        int index = 0;
        for (ArrayList<float[]> classSamples : dataset) {
            ArrayList<float[]> transformedClass = new ArrayList<>();
            for (float[] sample : classSamples) {
                transformedClass.add(Arrays.copyOf(lowDimEmbedding[index], numComponents));
                index++;
            }
            transformedDataset.add(transformedClass);
        }
        return transformedDataset;
    }

    // DATA INCREMENT
    // by polinomial features

    public ArrayList<ArrayList<float[]>> expandPolynomialFeatures(ArrayList<ArrayList<float[]>> dataset, int degree) {
        ArrayList<ArrayList<float[]>> expandedDataset = new ArrayList<>();

        for (ArrayList<float[]> classSamples : dataset) {
            ArrayList<float[]> expandedClass = new ArrayList<>();
            for (float[] sample : classSamples) {
                float[] expandedSample = generatePolynomialFeatures(sample, degree);
                expandedClass.add(expandedSample);
            }
            expandedDataset.add(expandedClass);
        }

        return expandedDataset;
    }

    private float[] generatePolynomialFeatures(float[] sample, int degree) {
        ArrayList<Float> expandedFeatures = new ArrayList<>();
        int numFeatures = sample.length;

        // Add original features first
        for (float value : sample) {
            expandedFeatures.add(value);
        }

        // Generate polynomial features up to the given degree
        for (int d = 2; d <= degree; d++) {
            for (int i = 0; i < numFeatures; i++) {
                expandedFeatures.add((float) Math.pow(sample[i], d)); // x_i^d
            }
        }

        // Convert ArrayList<Float> to float[]
        float[] expandedArray = new float[expandedFeatures.size()];
        for (int i = 0; i < expandedFeatures.size(); i++) {
            expandedArray[i] = expandedFeatures.get(i);
        }

        return expandedArray;
    }

    ////////// DECISION TREE
    /// //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    /// ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    /// ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    /// //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    public static class DecisionTree {
        int val; // Splitting feature index or class label
        DecisionTree yes; // Left subtree (yes branch)
        DecisionTree no; // Right subtree (no branch)

        DecisionTree(int val) {
            this.val = val;
        }

        DecisionTree(int val, DecisionTree yes, DecisionTree no) {
            this.val = val;
            this.yes = yes;
            this.no = no;
        }
    }

    public static DecisionTree createDecisionTree(float[][] data) {
        if (isPure(data)) {
            return new DecisionTree((int) data[0][data[0].length - 1]); // Return leaf node (1 or 0)
        }

        int bestFeature = findBestSplit(data);
        if (bestFeature == -1)
            return new DecisionTree((int) data[0][data[0].length - 1]);

        float[][] leftSplit = filterData(data, bestFeature, true);
        float[][] rightSplit = filterData(data, bestFeature, false);

        DecisionTree yesBranch = createDecisionTree(leftSplit);
        DecisionTree noBranch = createDecisionTree(rightSplit);

        return new DecisionTree(bestFeature, yesBranch, noBranch);
    }

    private static boolean isPure(float[][] data) {
        int lastCol = data[0].length - 1;
        float firstLabel = data[0][lastCol];

        for (float[] row : data) {
            if (row[lastCol] != firstLabel)
                return false;
        }
        return true;
    }

    private static int findBestSplit(float[][] data) {
        int numFeatures = data[0].length - 1;
        int bestFeature = -1;
        float bestGini = Float.MAX_VALUE;

        for (int feature = 0; feature < numFeatures; feature++) {
            float gini = computeGini(data, feature);
            if (gini < bestGini) {
                bestGini = gini;
                bestFeature = feature;
            }
        }
        return bestFeature;
    }

    private static float computeGini(float[][] data, int feature) {
        float[][] leftSplit = filterData(data, feature, true);
        float[][] rightSplit = filterData(data, feature, false);

        float leftWeight = (float) leftSplit.length / data.length;
        float rightWeight = (float) rightSplit.length / data.length;

        return leftWeight * giniImpurity(leftSplit) + rightWeight * giniImpurity(rightSplit);
    }

    private static float giniImpurity(float[][] data) {
        if (data.length == 0)
            return 0;
        int lastCol = data[0].length - 1;
        int countYes = 0, countNo = 0;

        for (float[] row : data) {
            if (row[lastCol] == 1)
                countYes++;
            else
                countNo++;
        }

        float probYes = (float) countYes / data.length;
        float probNo = (float) countNo / data.length;

        return 1 - (probYes * probYes + probNo * probNo);
    }

    private static float[][] filterData(float[][] data, int feature, boolean isYes) {
        ArrayList<float[]> filtered = new ArrayList<>();
        float threshold = findMedian(data, feature);

        for (float[] row : data) {
            if ((isYes && row[feature] <= threshold) || (!isYes && row[feature] > threshold)) {
                filtered.add(row);
            }
        }
        return filtered.toArray(new float[0][0]);
    }

    private static float findMedian(float[][] data, int feature) {
        double[] values = Arrays.stream(data).mapToDouble(row -> row[feature]).sorted().toArray();
        int mid = values.length / 2;
        return values.length % 2 == 0 ? (float) (values[mid - 1] + values[mid]) / 2 : (float) values[mid];
    }

    public static void printTree(DecisionTree node, String prefix) {
        if (node == null)
            return;
        if (node.yes == null && node.no == null) {
            System.out.println(prefix + "Class: " + node.val);
            return;
        }
        System.out.println(prefix + "Feature " + node.val + " ?");
        System.out.println(prefix + "├── Yes:");
        printTree(node.yes, prefix + "│   ");
        System.out.println(prefix + "└── No:");
        printTree(node.no, prefix + "    ");
    }

}
