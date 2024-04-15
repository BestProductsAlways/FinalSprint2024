import './style.css'; // Importing the CSS file

import { Link, Route, BrowserRouter as Router, Switch } from 'react-router-dom';
import React, { useState } from 'react';

import DivisionForm from './components/DivisionForm';
import DivisionList from './components/DivisionList';

function App() {
    const [selectedDivisionId, setSelectedDivisionId] = useState(null);

    const handleEditDivision = (id) => {
        setSelectedDivisionId(id);
    };

    const handleNewDivision = () => {
        setSelectedDivisionId(null);
    };

    return (
        <Router>
            <div className="container">
                <nav>
                    <ul>
                        <li><Link to="/">Division List</Link></li>
                        <li><Link to="/new">Create New Division</Link></li>
                    </ul>
                </nav>
                <Switch>
                    <Route path="/" exact>
                        <DivisionList onEdit={handleEditDivision} onDelete={handleNewDivision} />
                    </Route>
                    <Route path="/new">
                        <DivisionForm onSubmit={handleNewDivision} />
                    </Route>
                    <Route path="/edit/:id">
                        <DivisionForm divisionId={selectedDivisionId} onSubmit={handleNewDivision} />
                    </Route>
                </Switch>
            </div>
        </Router>
    );
}

export default App;
