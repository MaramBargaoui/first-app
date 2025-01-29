# Project Report

## 1. Project Description
This project is a comprehensive data analysis management system using Java, designed to handle various data ingestion, exploration, and visualization tasks. The system follows a structured object-oriented programming (OOP) approach, incorporating data processing, machine learning, and reporting capabilities. The aim is to provide a user-friendly interface where users can import, clean, analyze, and visualize data effectively within a chosen domain application.

Additionally, this project includes a **Movie Analysis System**, which focuses on analyzing movie datasets, including IMDb ratings, revenues, and production details. Users can explore trends, correlations, and machine learning predictions related to movie performance and audience preferences.

## 2. Objectives
### Data Ingestion & Processing
- Import data from different sources (CSV, Excel, databases).
- Clean and preprocess data by handling missing values, outliers, and normalization.
- Store data in relational or NoSQL databases.

### Data Exploration & Analysis
- Perform descriptive statistics (mean, median, mode, standard deviation).
- Generate data visualizations (charts, graphs, histograms).
- Conduct correlation, regression, and time series analysis.

### Machine Learning Integration
- Implement machine learning models using Apache Spark, Tribuo, and MLlib for predictive modeling and classification.
- Analyze factors influencing movie ratings, box office revenues, and audience engagement.

### Reporting & Dashboards
- Generate customizable reports and dashboards using JFreeChart, JFreeReport, and Apache Spark visualization tools.
- Provide insights on movie trends and performance analysis.

## 3. Project Structure
### Directories
- `.vscode/` - Configuration files for Visual Studio Code.
- `FrontendJava/bmt/` - Frontend Java components.
- `assets/` - Stores media and related resources.
- `hadoop/bin/` - Hadoop binary files for large-scale data processing.
- `libs/` - Required Java libraries.
- `node_modules/` - Node.js dependencies.
- `spark-3.5.4-bin-hadoop3/` - Apache Spark framework for big data processing.
- `src/` - Source code for the project.
- `target/` - Compiled Java artifacts.

### Files
- `Correlation.java` - Java class for correlation and regression analysis.
- `Plot Histogram.py` - Python script for plotting histograms.
- `dependency-reduced-pom.xml` - Maven dependency file.
- `index.js` - Node.js backend server for web interactions.
- `movies.db` - Database file for storing movie data.
- `package-lock.json` - Node.js dependency lock file.
- `package.json` - Node.js package configuration.
- `pom.xml` - Maven configuration for Java.
- `requirements.txt` - Python dependencies for data analysis.

## 4. Technologies Used
- **Programming Languages:** Java, Python, JavaScript, Scala, R, HTML.
- **Big Data:** Apache Spark, Hadoop.
- **Web Technologies:** Node.js.
- - **Data Storage:** SQLite.
- **Machine Learning Libraries:** Tribuo, MLlib.
- **Visualization Tools:** JFreeChart, Apache Spark visualization.

## 5. Object-Oriented Programming Concepts Used
- **Encapsulation:** Data and methods are encapsulated within classes to maintain integrity.
- **Inheritance:** Hierarchies of classes model relationships between data entities.
- **Polymorphism:** Methods support multiple implementations for flexibility.
- **Abstraction:** Key system behaviors are defined using interfaces and abstract classes.

## 6. System Architecture
### Presentation Layer
- Implements a user-friendly interface for users to interact with the system.
- Provides web-based data input and result visualization.

### Business Logic Layer
- Handles data ingestion, cleaning, analysis, visualization, and ML model execution.
- Processes movie datasets and generates insights on revenue trends and audience engagement.

### Data Access Layer
- Manages database interactions for data storage and retrieval.
- Handles large-scale movie datasets efficiently.

## 7. Relationships Between Components
1. **Java Backend** interacts with **Python scripts** for specialized data analysis.
2. **Node.js Web Interface** provides a frontend for querying and analyzing data.
3. **Apache Spark & Hadoop** process large-scale datasets efficiently.
4. **Machine Learning Models** predict movie performance based on historical data.

## 8. Project Timeline
- **Phase 1:** System design and architecture (1 week).
- **Phase 2:** Development of data ingestion and storage components (2 weeks).
- **Phase 3:** Development of data analysis and visualization features (3 weeks).

## 9. Conclusion
This project integrates Java, Python, and big data frameworks to develop a scalable, modular, and efficient data analysis management system. By leveraging object-oriented programming, machine learning, and visualization techniques, the system provides a comprehensive solution for data exploration and decision-making.

The **Movie Analysis System** adds further value by enabling detailed examination of movie datasets, revealing trends in audience preferences, revenue patterns, and key success factors in the entertainment industry.

