const express = require('express');
const path = require('path');
const app = express();

// Use the environment-provided port or default to 3001
const PORT = process.env.PORT || 3001;

// Serve static files from the "public" folder
app.use(express.static(path.join(__dirname, 'FrontendJava', 'bmt', 'frontend', 'public')));

// Serve the default route with the index.html file
app.get('/', (req, res) => {
  res.sendFile(path.join(__dirname, 'FrontendJava', 'bmt', 'frontend', 'public', 'index.html'));
});

// Start the server
app.listen(PORT, () => {
  console.log(`Server is running on http://localhost:${PORT}`);
});
app.get('/histograms.html', (req, res) => {
    res.sendFile(path.join(__dirname, 'FrontendJava', 'bmt', 'frontend', 'public', 'histograms.html'));
  });
  
  app.get('/correlation_and_regression.html', (req, res) => {
    res.sendFile(path.join(__dirname, 'FrontendJava', 'bmt', 'frontend', 'public', 'correlation_and_regression.html'));
  });
  