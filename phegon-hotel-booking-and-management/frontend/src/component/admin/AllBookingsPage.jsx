import React, { useState, useEffect } from 'react';
import ApiService from '../../service/ApiService';
import { useNavigate } from 'react-router-dom';
import './AllBookingsPage.css';

const AllBookingsPage = () => {
    const [bookings, setBookings] = useState([]);
    const [loading, setLoading] = useState(true);
    const [error, setError] = useState('');
    const navigate = useNavigate();

    useEffect(() => {
        const fetchBookings = async () => {
            try {
                const response = await ApiService.getAllBookings();
                setBookings(response.bookingList);
                setLoading(false);
            } catch (err) {
                console.error('Error fetching bookings:', err.message);
                setError('Failed to fetch bookings.');
                setLoading(false);
            }
        };

        fetchBookings();
    }, []);

    if (loading) {
        return <p>Loading bookings...</p>;
    }

    if (error) {
        return <p className="error-message">{error}</p>;
    }

    return (
        <div className="all-bookings-container">
            <h2>All Bookings</h2>
            {bookings.length > 0 ? (
                <div className="bookings-list">
                    {bookings.map((booking) => (
                        <div key={booking.id} className="booking-item">
                            <p><strong>Booking Code:</strong> {booking.bookingConfirmationCode}</p>
                            <p><strong>Check In Date:</strong> {booking.checkInDate}</p>
                            <p><strong>Check Out Date:</strong> {booking.checkOutDate}</p>
                            <p><strong>Room Number:</strong> {booking.roomNumber}</p>
                            <button
                                className="edit-room-button"
                                onClick={() => navigate(`/admin/edit-booking/${booking.bookingConfirmationCode}`)}
                            >
                                Manage Booking
                            </button>
                            
                        </div>
                    ))}
                </div>
            ) : (
                <p>No bookings available.</p>
            )}
        </div>
    );
};

export default AllBookingsPage;