import React, { useState, useEffect } from 'react';
import { useNavigate } from 'react-router-dom';
import { startOfMonth, endOfMonth, eachDayOfInterval, format, addMonths, subMonths, isBefore } from 'date-fns';
import ApiService from '../../service/ApiService';
import './ManageBookingsPage.css';

const ManageBookingsPage = () => {
    const [bookings, setBookings] = useState([]);
    const [currentMonth, setCurrentMonth] = useState(new Date());
    const [currentMonthDays, setCurrentMonthDays] = useState([]);
    const [selectedDate, setSelectedDate] = useState(null);
    const [bookingsForSelectedDate, setBookingsForSelectedDate] = useState([]);
    const navigate = useNavigate();

    useEffect(() => {
        const fetchBookings = async () => {
            try {
                const response = await ApiService.getAllBookings();
                const today = new Date();
                // Filter out past bookings
                const futureBookings = response.bookingList.filter(
                    (booking) => !isBefore(new Date(booking.checkOutDate), today)
                );
                setBookings(futureBookings);
            } catch (error) {
                console.error('Error fetching bookings:', error.message);
            }
        };

        fetchBookings();
    }, []);

    useEffect(() => {
        // Generate all days for the current month
        const daysInMonth = eachDayOfInterval({
            start: startOfMonth(currentMonth),
            end: endOfMonth(currentMonth),
        });
        setCurrentMonthDays(daysInMonth);
    }, [currentMonth]);

    const handleDayClick = (date) => {
        setSelectedDate(date);
        const formattedDate = format(date, 'yyyy-MM-dd');
        const filteredBookings = bookings.filter(
            (booking) => booking.checkInDate === formattedDate || booking.checkOutDate === formattedDate
        );
        setBookingsForSelectedDate(filteredBookings);
    };

    const handlePreviousMonth = () => {
        setCurrentMonth((prev) => subMonths(prev, 1));
    };

    const handleNextMonth = () => {
        setCurrentMonth((prev) => addMonths(prev, 1));
    };

    return (
        <div className="calendar-container">
            <h2>Manage Bookings</h2>
            <div className="calendar-header">
                <button onClick={handlePreviousMonth}>&lt;</button>
                <h3>{format(currentMonth, 'MMMM yyyy')}</h3>
                <button onClick={handleNextMonth}>&gt;</button>
            </div>
            <div className="calendar-grid">
                {['Sun', 'Mon', 'Tue', 'Wed', 'Thu', 'Fri', 'Sat'].map((day) => (
                    <div key={day} className="calendar-day-header">
                        {day}
                    </div>
                ))}
                {currentMonthDays.map((day) => (
                    <div
                        key={day}
                        className={`calendar-day ${isBefore(day, new Date()) ? 'past-day' : ''}`}
                        onClick={() => !isBefore(day, new Date()) && handleDayClick(day)}
                    >
                        {format(day, 'd')}
                    </div>
                ))}
            </div>

            {selectedDate && (
                <div className="bookings-for-date">
                    <h3>Bookings for {format(selectedDate, 'yyyy-MM-dd')}</h3>
                    {bookingsForSelectedDate.length > 0 ? (
                        bookingsForSelectedDate.map((booking) => (
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
                        ))
                    ) : (
                        <p>No bookings for this date.</p>
                    )}
                </div>
            )}
        </div>
    );
};

export default ManageBookingsPage;