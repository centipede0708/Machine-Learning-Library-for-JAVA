JavaML: Lightweight ML & Plotting Library
JavaML is a lightweight, pure Java library for basic machine learning tasks and data visualization. It is written from scratch with no external dependencies beyond the standard Java AWT and Swing libraries.

This library provides simple implementations of common algorithms, making it a useful tool for learning or for projects where external dependencies are not desired.

Features
Data Generation: Create random multi-dimensional datasets for testing.

Clustering: K-Means clustering algorithm.

Regression: Polynomial regression trained with gradient descent.

Dimensionality Reduction: Principal Component Analysis (PCA).

Plotting: Built-in 2D and 3D data visualization tools using Java Swing.

2D scatter plots for clusters.

2D scatter plots with regression curves.

Basic 3D scatter plots.

Setup
Since this is a single-file library, you can directly include the JavaML.java file in your project.

You will need a Java Development Kit (JDK) 8 or higher to compile and run the code.

API & Usage
Here are the main public methods available in the JavaML class.

Data Generation
RandomPointGenerator Generates a nested ArrayList of random, multi-dimensional points.

Java

public ArrayList<ArrayList<float[]>> RandomPointGenerator(
    int outerSize,
    int innerSize,
    int dimensions,
    float minRange,
    float maxRange
);
Clustering (K-Means)
Clustering Performs K-Means clustering on a dataset. It takes a list of points and returns a list of clusters.

Java

public static ArrayList<ArrayList<float[]>> Clustering(
    ArrayList<ArrayList<float[]>> inputPoints,
    int numberOfClusters
);
plot Visualizes the result of the clustering algorithm in a 2D scatter plot. Each cluster is assigned a different color.

Java

public static void plot(ArrayList<ArrayList<float[]>> result);
Polynomial Regression
TrainPolynomialRegressionModel Trains a polynomial regression model using gradient descent. It returns an ArrayList of the learned coefficients (w0, w1, w2, ...).

Java

public static ArrayList<Float> TrainPolynomialRegressionModel(
    ArrayList<ArrayList<float[]>> dataset,
    int degree,
    float learningRate,
    int iterations
);
plotRegression Plots the original 2D dataset and the learned polynomial regression curve.

Java

public static void plotRegression(
    ArrayList<ArrayList<float[]>> dataset,
    ArrayList<Float> coeff
);
Dimensionality Reduction (PCA)
PCA Performs Principal Component Analysis to reduce the dimensionality of a dataset.

Note: The SVD and covariance matrix calculations must be fully implemented for this to be functional.

Java

public ArrayList<ArrayList<float[]>> PCA(
    ArrayList<ArrayList<float[]>> dataset,
    int numComponents
);
3D Plotting
Plot3D Renders a simple 3D scatter plot of the dataset in a new Swing window.

Java

public static void Plot3D(ArrayList<ArrayList<float[]>> dataset);
Example
Here's a simple main method to demonstrate how to use the library for clustering and regression.

Java

import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {
        JavaML ml = new JavaML();

        // =========== K-Means Clustering Example ===========

        // 1. Generate random data for clustering
        // We'll create 3 distinct groups
        ArrayList<ArrayList<float[]>> cluster1 = ml.RandomPointGenerator(1, 50, 2, 0.0f, 10.0f);
        ArrayList<ArrayList<float[]>> cluster2 = ml.RandomPointGenerator(1, 50, 2, 20.0f, 30.0f);
        ArrayList<ArrayList<float[]>> cluster3 = ml.RandomPointGenerator(1, 50, 2, 40.0f, 50.0f);

        // Combine them into one dataset
        ArrayList<ArrayList<float[]>> clusterDataset = new ArrayList<>();
        clusterDataset.addAll(cluster1);
        clusterDataset.addAll(cluster2);
        clusterDataset.addAll(cluster3);

        // 2. Perform clustering (K=3)
        int k = 3;
        ArrayList<ArrayList<float[]>> clusteredResult = JavaML.Clustering(clusterDataset, k);

        // 3. Plot the cluster results
        JavaML.plot(clusteredResult);
        System.out.println("Displaying cluster plot...");


        // =========== Polynomial Regression Example ===========

        // 1. Generate data for regression (e.g., y = 0.5x^2 - 1x + 3 + noise)
        ArrayList<ArrayList<float[]>> regData = ml.RandomPointGenerator(1, 100, 2, -10.0f, 10.0f);
        ArrayList<float[]> points = regData.get(0);
        for (float[] p : points) {
            float x = p[0];
            // Add noise
            float noise = (float) (Math.random() * 8.0 - 4.0);
            // y = 0.5x^2 - x + 3 + noise
            p[1] = (0.5f * x * x) - (1.0f * x) + 3.0f + noise;
        }

        // 2. Train the model (degree 2)
        ArrayList<Float> coefficients = JavaML.TrainPolynomialRegressionModel(
            regData, 2, 0.001f, 10000
        );

        // 3. Plot the regression
        JavaML.plotRegression(regData, coefficients);
        System.out.println("Displaying regression plot...");
    }
}
To-Do / Future Work
Complete the SVD implementation for PCA.

Implement the placeholder Reinforcement Learning section.

Add more ML algorithms (e.g., Logistic Regression, SVM).

Improve plotting capabilities (axis labels, legends, interactivity).

License
This project is open-source. Feel free to use, modify, and distribute it. (Consider adding a formal license like MIT or Apache 2.0).
