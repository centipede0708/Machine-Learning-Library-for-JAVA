JavaML: A Simple ML & Plotting Library
A lightweight, zero-dependency Java library for basic machine learning algorithms (K-Means, Polynomial Regression) and 2D/3D data visualization, built from scratch using only Java AWT and Swing.

This project is intended for educational purposes, demonstrating how ML algorithms and plotting tools can be implemented from the ground up without any external dependencies.

Features
K-Means Clustering: Implements the K-Means clustering algorithm for n-dimensional data.

Polynomial Regression: Trains a multi-degree polynomial regression model using gradient descent.

Data Generation: Includes a RandomPointGenerator to create test datasets with specified dimensions and ranges.

2D/3D Plotting: Uses Java Swing to render data in 2D and 3D.

Scatter plots for clustered data (with automatic coloring).

Scatter plots with a fitted regression curve.

Basic 3D scatter plots with simple axis.

Dimensionality Reduction: Contains a partial implementation of Principal Component Analysis (PCA).

Setup
This is a single-file library. To use it, simply download JavaML.java and add it to your project.

Requirements:

Java Development Kit (JDK) 8 or higher.

How to Use
Below are examples of how to use the main features of the library. You can run this code in your own main method.

Example 1: K-Means Clustering
This example generates 3 distinct groups of 2D points, combines them, and then uses the Clustering algorithm to find the original groups.

Generate 3 separate clusters of 50 points each.

Combine them into one dataset.

Run the K-Means algorithm with K=3.

Plot the results in a new window.

Example 2: Polynomial Regression
This example generates noisy quadratic data (y = 0.5x^2 - x + 2 + noise) and then fits a 2nd-degree polynomial to it.

Generate 100 random (x, y) points.

Create a quadratic relationship (e.g., y = (0.5f * x * x) - x + 2.0f + noise).

Train the model to find the coefficients for a 2nd-degree polynomial.

Plot the data points and the fitted regression curve.

API Overview
Data Generation
RandomPointGenerator(int outerSize, int innerSize, int dimensions, float minRange, float maxRange): Generates a list of lists of random points.

Clustering
Clustering(ArrayList<ArrayList<float[]>> inputPoints, int numberOfClusters): Sorts input points into K clusters.

plot(ArrayList<ArrayList<float[]>> result): Opens a 2D Swing window to visualize the clusters.

Regression
TrainPolynomialRegressionModel(ArrayList<ArrayList<float[]>> dataset, int degree, float learningRate, int iterations): Returns an ArrayList<Float> of coefficients [w0, w1, w2, ...] for the polynomial y = w0 + w1*x + w2*x^2 + ....

plotRegression(ArrayList<ArrayList<float[]>> dataset, ArrayList<Float> coeff): Opens a 2D Swing window to plot the original data and the regression curve.

Plotting
Plot3D(ArrayList<ArrayList<float[]>> dataset): Opens a 3D Swing window to visualize the dataset (uses the first 3 dimensions).

Dimensionality Reduction
PCA(ArrayList<ArrayList<float[]>> dataset, int numComponents): Reduces the dimensionality of the dataset. Note: This implementation is incomplete as it depends on an un-implemented SVD method.

Project Status & Limitations
This project is a functional proof-of-concept for educational use. It is not intended for production environments.

Incomplete PCA: The PCA method is a stub and will not function without a complete implementation of computeSVD.

No Optimizations: Algorithms are written for clarity, not performance.

Basic Plotting: The Swing plots are not interactive (no zoom, pan, or hover).

Basic UI: Plotting windows are basic JFrame instances. They might not close gracefully in all IDEs.
