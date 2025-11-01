# JavaML – Machine Learning Playground in Java

This project is a custom-built **Machine Learning Visualization Framework** written entirely in **Java (AWT + Swing)**.  
It demonstrates core ML operations like dataset generation, dimensionality reduction (PCA, UMAP), clustering, and polynomial regression — all visualized graphically.

---

## 🚀 Features
- Random multi-dimensional dataset generation
- Polynomial feature expansion
- PCA & UMAP dimensionality reduction
- K-means-style clustering visualization
- Polynomial regression model training & plotting
- 2D and 3D visualization using AWT/Swing
- Supports arbitrary dataset sizes and dimensions

---

## ⚙️ Demonstrated Functions

| Function | Description |
|-----------|--------------|
| `RandomPointGenerator(int outerSize, int innerSize, int dimensions, float minRange, float maxRange)` | Generates random clustered points |
| `expandPolynomialFeatures(dataset, degree)` | Expands dataset features to higher-degree polynomial terms |
| `PCA(dataset, numComponents)` | Performs Principal Component Analysis for dimensionality reduction |
| `UMAP(dataset, numComponents, nNeighbors)` | Applies UMAP for non-linear dimensionality reduction |
| `Clustering(inputPoints, numberOfClusters)` | Groups points into clusters |
| `TrainPolynomialRegressionModel(dataset, degree, learningRate, iterations)` | Trains regression coefficients |
| `plotRegression(dataset, coeff)` | Plots regression results |
| `Plot3D(dataset)` | Displays 3D scatter plot |
| `plot(result)` | General-purpose 2D plotter |
| `printCluster(result)` | Prints clusters to console |
| `printFloatArrayList(list)` | Prints individual float arrays for debugging |

---

## 🧩 How the Demo Works
<img width="637" height="490" alt="Screenshot 2025-11-01 180632" src="https://github.com/user-attachments/assets/124e1553-a896-4fa1-b4a8-ece32ce0e45b" />


## 🖥️ Example Output
### random point generation
<img width="1476" height="844" alt="Screenshot 2025-11-01 180952" src="https://github.com/user-attachments/assets/86fd774d-0919-4083-8692-73fb2cbb900d" />

### cluster formation
<img width="1535" height="859" alt="Screenshot 2025-11-01 180932" src="https://github.com/user-attachments/assets/c3256b75-81a7-4797-a57b-5e05bf5712df" />

### polynomial regresion plot
<img width="1553" height="865" alt="Screenshot 2025-11-01 180903" src="https://github.com/user-attachments/assets/4f49663e-77e4-4ce1-8c9b-15037c0c8a0e" />




