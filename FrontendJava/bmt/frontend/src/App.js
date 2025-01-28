import React from 'react';
import { BrowserRouter as Router, Route, Switch } from 'react-router-dom';
import MoviesLibrary from './components/MoviesLibrary';
import IMDBRatings from './components/IMDBRatings';
import MoviesRevenues from './components/MoviesRevenues';
import DescriptiveStats from './components/DescriptiveStats';
import CorrelationCalculator from './components/CorrelationCalculator';
import RegressionAnalysis from './components/RegressionAnalysis';
import Home from './components/Home';
import Login from './components/Login';
import ThankYou from './components/ThankYou';


function App() {
  return (
    <Router>
      <div className="App">
        <Switch>
          <Route exact path="/" component={Home} />
          <Route path="/movies-library" component={MoviesLibrary} />
          <Route path="/imdb-ratings" component={IMDBRatings} />
          <Route path="/movies-revenues" component={MoviesRevenues} />
          <Route path="/descriptive-stats" component={DescriptiveStats} />
          <Route path="/correlation-calculator" component={CorrelationCalculator} />
          <Route path="/regression-analysis" component={RegressionAnalysis} />
          <Route path="/login" component={Login} />
          <Route path="/thank-you" component={ThankYou} />
        </Switch>
      </div>
    </Router>
  );
}


export default App;



