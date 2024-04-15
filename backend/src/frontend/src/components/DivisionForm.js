import './style.css';

import React, { useEffect, useState } from 'react';

import axios from 'axios';

// Import the CSS file

function DivisionForm({ divisionId, onSubmit }) {
    const [division, setDivision] = useState({
        name: '',
        startBirthYear: '',
        endBirthYear: '',
    });

    useEffect(() => {
        if (divisionId) {
            // Fetch the division data to edit
            axios.get(`/division/${divisionId}`).then((response) => {
                setDivision(response.data);
            });
        }
    }, [divisionId]);

    const handleChange = (e) => {
        setDivision({ ...division, [e.target.name]: e.target.value });
    };

    const handleSubmit = (e) => {
        e.preventDefault();
        if (divisionId) {
            // Update division
            axios.put(`/division/${divisionId}`, division).then((response) => {
                onSubmit(response.data);
            });
        } else {
            // Create new division
            axios.post('/division', division).then((response) => {
                onSubmit(response.data);
            });
        }
    };

    return (
        <form onSubmit={handleSubmit} className="container">
            <div className="form-group">
                <label className="label">Name</label>
                <input
                    type="text"
                    name="name"
                    value={division.name}
                    onChange={handleChange}
                    required
                    className="input"
                />
            </div>
            <div className="form-group">
                <label className="label">Start Birth Year</label>
                <input
                    type="text"
                    name="startBirthYear"
                    value={division.startBirthYear}
                    onChange={handleChange}
                    required
                    className="input"
                />
            </div>
            <div className="form-group">
                <label className="label">End Birth Year</label>
                <input
                    type="text"
                    name="endBirthYear"
                    value={division.endBirthYear}
                    onChange={handleChange}
                    required
                    className="input"
                />
            </div>
            <button type="submit" className="button">
                {divisionId ? 'Update Division' : 'Create Division'}
            </button>
        </form>
    );
}

export default DivisionForm;
