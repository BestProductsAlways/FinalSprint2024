import './style.css';

import React, { useEffect, useState } from 'react';

import axios from 'axios';

function DivisionList({ onEdit, onDelete }) {
    const [divisions, setDivisions] = useState([]);

    useEffect(() => {
        // Fetch the list of divisions from the API
        axios.get('/division').then((response) => {
            setDivisions(response.data);
        });
    }, []);

    const handleDelete = (id) => {
        axios.delete(`/division/${id}`).then(() => {
            setDivisions(divisions.filter((division) => division.id !== id));
            onDelete();
        });
    };

    return (
        <div>
            <h1>Division List</h1>
            <table className="table">
                <thead>
                    <tr>
                        <th>ID</th>
                        <th>Name</th>
                        <th>Start Birth Year</th>
                        <th>End Birth Year</th>
                        <th>Actions</th>
                    </tr>
                </thead>
                <tbody>
                    {divisions.map((division) => (
                        <tr key={division.id}>
                            <td>{division.id}</td>
                            <td>{division.name}</td>
                            <td>{division.startBirthYear}</td>
                            <td>{division.endBirthYear}</td>
                            <td>
                                <button onClick={() => onEdit(division.id)} className="button">Edit</button>
                                <button onClick={() => handleDelete(division.id)} className="button">Delete</button>
                            </td>
                        </tr>
                    ))}
                </tbody>
            </table>
        </div>
    );
}

export default DivisionList;
