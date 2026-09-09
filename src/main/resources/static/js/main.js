// main.js - Complete JavaScript
const API_BASE = '/api';

// Load featured cars on homepage
async function loadFeaturedCars() {
    try {
        const response = await fetch(`${API_BASE}/cars/featured`);
        const cars = await response.json();
        
        const container = document.getElementById('featuredCars');
        if (!container) return;
        
        if (cars.length === 0) {
            container.innerHTML = '<p class="no-cars">No featured cars available</p>';
            return;
        }
        
        container.innerHTML = cars.map(car => createCarCard(car)).join('');
    } catch (error) {
        console.error('Error loading featured cars:', error);
        const container = document.getElementById('featuredCars');
        if (container) {
            container.innerHTML = '<p class="error">Failed to load cars. Please try again later.</p>';
        }
    }
}

// Create car card HTML
function createCarCard(car) {
    const imageUrl = car.imageUrl || 'https://via.placeholder.com/400x300?text=No+Image';
    const statusClass = car.status === 'AVAILABLE' ? 'status-available' : 
                       car.status === 'SOLD' ? 'status-sold' : 'status-reserved';
    
    return `
        <div class="car-card" onclick="window.location.href='/car-details?id=${car.id}'">
            <img src="${imageUrl}" alt="${car.make} ${car.model}" class="car-image">
            <div class="car-info">
                <div class="car-title">${car.make} ${car.model}</div>
                <div class="car-price">$${car.price.toLocaleString()}</div>
                <div class="car-details">
                    <span><i class="fas fa-calendar"></i> ${car.year}</span>
                    <span><i class="fas fa-tachometer-alt"></i> ${car.mileage.toLocaleString()} mi</span>
                    <span><i class="fas fa-gas-pump"></i> ${car.fuelType}</span>
                </div>
                <span class="car-status ${statusClass}">${car.status}</span>
            </div>
        </div>
    `;
}

// Search cars
function searchCars() {
    const query = document.getElementById('searchInput')?.value;
    if (query && query.trim()) {
        window.location.href = `/cars?search=${encodeURIComponent(query.trim())}`;
    }
}

// Load cars page with filters
async function loadCarsPage() {
    const urlParams = new URLSearchParams(window.location.search);
    const search = urlParams.get('search') || '';
    const make = document.getElementById('makeFilter')?.value || '';
    const model = document.getElementById('modelFilter')?.value || '';
    const minPrice = document.getElementById('minPrice')?.value || '';
    const maxPrice = document.getElementById('maxPrice')?.value || '';
    const fuelType = document.getElementById('fuelType')?.value || '';
    const transmission = document.getElementById('transmission')?.value || '';
    
    let url = `${API_BASE}/cars/search?page=0&size=12`;
    if (make) url += `&make=${encodeURIComponent(make)}`;
    if (model) url += `&model=${encodeURIComponent(model)}`;
    if (minPrice) url += `&minPrice=${encodeURIComponent(minPrice)}`;
    if (maxPrice) url += `&maxPrice=${encodeURIComponent(maxPrice)}`;
    if (fuelType) url += `&fuelType=${encodeURIComponent(fuelType)}`;
    if (transmission) url += `&transmission=${encodeURIComponent(transmission)}`;
    if (search) url += `&search=${encodeURIComponent(search)}`;
    
    try {
        const response = await fetch(url);
        const data = await response.json();
        
        const container = document.getElementById('carsGrid');
        const countSpan = document.getElementById('carCount');
        
        if (!container) return;
        
        if (data.content && data.content.length > 0) {
            container.innerHTML = data.content.map(car => createCarCard(car)).join('');
            if (countSpan) {
                countSpan.textContent = `${data.totalElements} cars found`;
            }
        } else {
            container.innerHTML = '<p class="no-cars">No cars found matching your criteria</p>';
            if (countSpan) {
                countSpan.textContent = '0 cars found';
            }
        }
    } catch (error) {
        console.error('Error loading cars:', error);
        const container = document.getElementById('carsGrid');
        if (container) {
            container.innerHTML = '<p class="error">Failed to load cars. Please try again later.</p>';
        }
    }
}

// Load makes for filter
async function loadMakes() {
    try {
        const response = await fetch(`${API_BASE}/cars/makes`);
        const makes = await response.json();
        
        const select = document.getElementById('makeFilter');
        if (!select) return;
        
        // Clear existing options except the first one
        select.innerHTML = '<option value="">All Makes</option>';
        
        makes.forEach(make => {
            const option = document.createElement('option');
            option.value = make;
            option.textContent = make;
            select.appendChild(option);
        });
    } catch (error) {
        console.error('Error loading makes:', error);
    }
}

// Load models based on selected make
async function loadModels(make) {
    if (!make) {
        const select = document.getElementById('modelFilter');
        if (select) {
            select.innerHTML = '<option value="">All Models</option>';
        }
        return;
    }
    
    try {
        const response = await fetch(`${API_BASE}/cars/makes/${encodeURIComponent(make)}/models`);
        const models = await response.json();
        
        const select = document.getElementById('modelFilter');
        if (!select) return;
        
        select.innerHTML = '<