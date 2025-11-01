🧠 JavaML – Machine Learning Visualizer in Java (Swing + AWT)

JavaML is a lightweight visual machine learning and data visualization framework built entirely using Java Swing and AWT.
It provides interactive visualizations for:

Random point generation
2D & 3D plotting
Polynomial regression (with live curve fitting)
K-Means–style clustering visualization

🚀 Features
🧩 1. Random Point Generator
Generates multi-dimensional points with customizable:
Outer and inner list sizes
Value range (minRange, maxRange)
Dimensions (2D, 3D, n-D)

ArrayList<ArrayList<float[]>> data = RandomPointGenerator(3, 50, 2, -10, 10);

📈 2. 2D Plot Visualization
Plots 2D clusters or datasets inside a Swing JFrame window.

JavaML.plot(dataset);

Automatically scales axes
Uses distinct colors for different clusters
Supports live rendering of points

🧊 3. 3D Plot Visualization
Projects 3D points with grid and labeled axes.

JavaML.Plot3D(dataset);

3D-like perspective drawn using 2D transformations
Displays X, Y, Z axes with ticks and labeled values
Visually appealing orange data points on a gray plane

📉 4. Polynomial Regression
Train and visualize multi-degree regression models using gradient descent.

ArrayList<Float> coeff = TrainPolynomialRegressionModel(dataset, degree, learningRate, iterations);
JavaML.plotRegression(dataset, coeff);

Supports any polynomial degree
Visualizes regression curve in magenta over dataset
Automatic scaling for axes
Displays ticks and axis labels dynamically

🎯 5. K-Means–Style Clustering
Groups input points into k clusters and visualizes the result.

ArrayList<ArrayList<float[]>> clustered = Clustering(data, 3);
JavaML.plot(clustered);

Automatically assigns colors to each cluster
Uses Euclidean distance
Dynamically updates centroids until convergence


🖥️ Tech Stack
Language: Java
GUI: Swing & AWT

Core Concepts:
Gradient Descent
K-Means Clustering
Polynomial Regression
2D/3D Visualization

📜 License
This project is open-source and free to use for educational or experimental purposes.

👤 Author
Developed by: Tanmay
Branch: Electronics and Communication Engineering (ECE)
Focus: Machine Learning + Visualization + Algorithms
