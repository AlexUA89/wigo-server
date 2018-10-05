const React = require('react');
const ReactDOM = require('react-dom');
//const client = require('./client');

import logo from './../resources/static/images/logo.svg';
import './../resources/static/main.css';
import Map from './components/Map.js'

ReactDOM.render(<div className="App">
   <header className="App-header">
    <img src={logo} className="App-logo" alt="logo"/>
    <h1 className="App-title">Welcome to React</h1>
    </header>
    <Map/>
   </div>, document.getElementById('root'));
