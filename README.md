# mad-maps

[![GitHub Actions](https://github.com/Adamratkai/mad-maps/actions/workflows/maven.yml/badge.svg)](https://github.com/Adamratkai/mad-maps/actions/workflows/maven.yml)

<details>
<summary><h2><strong>Table of Contents</strong></h2></summary>

- [About the Project](#about-the-project)
- [Built With](#built-with)
- [Contributors](#contributors)
- [Getting Started](#getting-started)
    - [Prerequisites](#prerequisites)
    - [Installation Steps](#installation-steps)
- [Usage](#usage)
- [Acknowledgements](#acknowledgements)

</details>


## About The Project

Looking for a simple way to plan your next trip?
**Mad-Maps** is an interactive travel planning web application that helps create **personalized itineraries** with the help of integrated **Google Maps** features and relevant place recommendations. Whether you're exploring a new city or planning your next getaway, Mad-Maps simplifies the process of finding, selecting, and organizing your trip destinations.

### Features

- 🗺️ **Google Maps integration** with markers showing the active location
- 🔍 **Autocomplete search bar** with place type filters (e.g. restaurant, ATM, sights)
- 📌 **Location-based recommendations** using distance, rating, and relevance
- 🧳 **Trip builder** with expandable/collapsible activity cards
- 🔐 **JWT-based authentication** with role-based access control
- 🐳 **Dockerized** with Docker Compose
- 🔄 **CI pipeline** using GitHub Actions (build + test)

Mad-Maps is designed for performance, reliability, and ease of use—whether you're a casual traveler or an itinerary perfectionist.

<p align="center">
<img src="https://github.com/user-attachments/assets/708e5117-5d3c-4a93-923b-bba91c080b9f" width="600" alt="Screenshot of Trip Planner"/> <br>
</p>


## Built With

- **Backend:**  
  [![Spring Boot](https://img.shields.io/badge/Spring%20Boot-6DB33F?style=for-the-badge&logo=spring&logoColor=white)](https://spring.io/projects/spring-boot)

- **Frontend:**  
  [![React](https://img.shields.io/badge/React-20232A?style=for-the-badge&logo=react&logoColor=61DAFB)](https://reactjs.org/)  <br>
  [![NGINX](https://img.shields.io/badge/NGINX-009639?style=for-the-badge&logo=nginx&logoColor=white)](https://www.nginx.com/) <br>
  [![TailwindCSS](https://img.shields.io/badge/TailwindCSS-06B6D4?style=for-the-badge&logo=tailwindcss&logoColor=white)](https://tailwindcss.com/)

- **Database:**  
  [![PostgreSQL](https://img.shields.io/badge/PostgreSQL-4169E1?style=for-the-badge&logo=postgresql&logoColor=white)](https://www.postgresql.org/)

- **Containerization:**  
  [![Docker](https://img.shields.io/badge/Docker-2496ED?style=for-the-badge&logo=docker&logoColor=white)](https://www.docker.com/)  
  


## Contributors

| Name         | GitHub Profile                             |
|--------------|--------------------------------------------|
| Dóra Erdélyi | [doraerdelyi](https://github.com/doraerdelyi)|
| Marcell Nagy | [Dyrun](https://github.com/Dyrun)          |
| Ádám Ratkai  | [Adamratkai](https://github.com/Adamratkai)|


## Getting Started

To get a local copy up and running, follow these steps:

### Prerequisites

- #### Docker Desktop
  ➡️ [https://www.docker.com/products/docker-desktop/](https://www.docker.com/products/docker-desktop/)

### Installation Steps

1. Open a **terminal** and navigate to the directory where you would like to save the repository.

2. **Clone the repository** to your machine by executing the command below in your **terminal**, then proceed with the steps below.
   ```bash
   git clone https://github.com/Adamratkai/mad-maps.git
   ```

To simplify setup, an `example.env` file is already provided with the necessary **environment variables**, except the **API_KEY**.

#### Docker

1. **Ensure Docker is Running**
    - Start **Docker Desktop** or the **Docker daemon** on your system.

2. **Rename `example.env` File**
    - In the repository folder find the `example.env` file
    - Rename the `example.env` file to `.env`, simply remove the `example` part.

3. **Add Google API Key**
    - Add your Google API key to the **API_KEY** field in the `.env` file (you can request an api key here: https://console.cloud.google.com/freetrial)

4. **Create and Run Docker Container**
    - Execute the following command in your terminal:
      ```bash
      docker compose up --build
      ```

5. **Access the Application**
    - Open your browser and visit:  
      [http://localhost:3000](http://localhost:3000)

6. **Stopping the Application**
   - In your **terminal** press `Ctrl + C`
   - If you want to **stop and remove the containers**, but **keep the database data** for future runs, execute:
     ```bash
     docker compose down
     ```
     In this case, the database will **persist** between runs, and your data will still be available next time you start the application.

   - If you want to **stop, remove the containers and delete the database data**, execute:
     ```bash
     docker compose down -v
     ```
     In this case, the database and all stored data will be completely removed.


## Usage

### Planning your trip

1. **Register** a new account or **log in** if you already have one.

2. **Create a New Trip**

- After logging in, you’ll be taken to your list of trips. If you have 
  existing trips, click on any of them to view details.
- To create a new trip, click on the **Add Trip** button. Once your new trip is 
  created, click on it to start planning.

3. **Choose a Location on the Map**

    - On the trip planner page, you can select a location:
      - **Manually**, by zooming in, panning the map, and clicking on a spot.
      - **Using the search field**, which features autocomplete and a place type dropdown
        (e.g., restaurant, hotel, etc.).

4. **Choose from Recommendations**
    - After selecting a location, the app will show the **20 most relevant recommendations**
      based on factors like distance, rating, and category.
    - If you selected a place type (e.g., restaurants), only recommendations of that type will be shown.
    - Click on the **Details** button for each place to view pictures, ratings, hours, and price range.
    - To add a recommended place to your trip, click on the **Add Place** button.
   
5. **Manage Your Trip**
    - The places you’ve added to your trip will be displayed in the trip’s box in the order you added them.
    - You can expand each place to reveal its details again.
<p align="center">
<img src="https://github.com/user-attachments/assets/ff488526-e087-4e43-b433-f9c9b8277b03" width="600" alt="Screenshot of Trip Planner" />

<img src="https://github.com/user-attachments/assets/b73482fa-77c4-4ad4-a991-95094e1860b1" width="600" alt="Screenshot of Trip Planner"/>

<img src="https://github.com/user-attachments/assets/01077170-9a33-468d-a500-38d60d3275c5" width="600" alt="Screenshot of Trip Planner" />
</p>

## Acknowledgements

- [Best-README-Template](https://github.com/othneildrew/Best-README-Template) for the Readme structure
- [Shields.io](https://shields.io/) for the badges
- [Google Maps API](https://developers.google.com/maps/documentation/javascript) for the Google Maps
- [Google Places API](https://developers.google.com/maps/documentation/places/web-service) for the places