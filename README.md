# 🔍 Pokemon Search Engine

A full-stack web application for searching and exploring Pokemon information with a modern, responsive interface.

## 🎯 Features

- **Search Functionality**: Search Pokemon by name or ID with real-time results
- **Detailed Pokemon Information**: View comprehensive details including:
  - Stats (HP, Attack, Defense, Speed, etc.)
  - Types and abilities
  - Moves
  - Sprites and images
- **Popular Pokemon**: Discover the most popular Pokemon
- **Responsive Design**: Works seamlessly on desktop, tablet, and mobile devices
- **Caching**: Optimized performance with server-side caching
- **REST API**: Full RESTful API for Pokemon data

## 🏗️ Tech Stack

### Backend
- **Language**: Java 25
- **Framework**: Spring Boot 3.2.5
- **Build Tool**: Maven
- **API Source**: PokéAPI v2
- **Caching**: Spring Cache with Redis support
- **Web Client**: Spring WebClient for async HTTP calls

### Frontend
- **Framework**: React with Vite
- **Styling**: CSS Modules
- **HTTP Client**: Axios
- **Build Tool**: Vite
- **Package Manager**: PNPM

## 📋 Prerequisites

- Java 25 or higher
- Node.js 18+ 
- PNPM or npm
- Maven 3.6+

## 🚀 Getting Started

### Backend Setup

1. Navigate to the backend directory:
```bash
cd backend
```

2. Run the Spring Boot application:
```bash
mvn spring-boot:run
```

The backend will start on `http://localhost:8080`

### Frontend Setup

1. Navigate to the frontend directory:
```bash
cd frontend
```

2. Install dependencies:
```bash
pnpm install
```

3. Start the development server:
```bash
pnpm run dev
```

The frontend will start on `http://localhost:5173` (or another available port)

## 📦 Project Structure

```
Pokemon-Search-Engine/
├── backend/
│   ├── src/
│   │   └── main/java/com/pokedex/
│   │       ├── config/          # Configuration classes
│   │       ├── controller/       # REST endpoints
│   │       ├── dto/              # Data Transfer Objects
│   │       ├── exception/        # Exception handlers
│   │       └── service/          # Business logic
│   └── pom.xml
├── frontend/
│   ├── src/
│   │   ├── components/           # React components
│   │   ├── pages/                # Page components
│   │   ├── services/             # API services
│   │   └── App.jsx
│   ├── index.html
│   ├── vite.config.js
│   └── package.json
└── README.md
```

## 🔗 API Endpoints

- `GET /api/pokemon/{nameOrId}` - Get Pokemon details by name or ID
- `GET /api/pokemon/search?query={query}&limit={limit}` - Search Pokemon
- `GET /api/pokemon/popular?limit={limit}` - Get popular Pokemon

## 🎨 UI Components

- **Navbar**: Navigation header with branding
- **SearchBar**: Search input with suggestions
- **PokemonCard**: Display Pokemon information in card format
- **TypeBadge**: Visual representation of Pokemon types
- **StatsBar**: Visual stats display with progress bars

## ⚙️ Configuration

### Backend Configuration
Edit `backend/src/main/resources/application.properties` to customize:
- Server port
- Cache settings
- CORS configuration
- Logging levels

### Frontend Configuration
Edit `frontend/vite.config.js` to customize:
- API base URL
- Build output
- Dev server settings

## 🧪 Testing

### Backend Tests
```bash
cd backend
mvn test
```

### Frontend Tests
```bash
cd frontend
pnpm run test
```

## 🚢 Deployment

### Docker
Build and run with Docker:
```bash
docker build -t pokemon-search-engine .
docker run -p 8080:8080 pokemon-search-engine
```

### Production Build
```bash
# Frontend
cd frontend
pnpm run build

# Backend
cd backend
mvn clean package
```

## 📝 API Documentation

### Search Pokemon
```bash
curl "http://localhost:8080/api/pokemon/search?query=pikachu&limit=10"
```

### Get Pokemon Details
```bash
curl "http://localhost:8080/api/pokemon/pikachu"
```

### Get Popular Pokemon
```bash
curl "http://localhost:8080/api/pokemon/popular?limit=5"
```

## 🤝 Contributing

1. Fork the repository
2. Create a feature branch (`git checkout -b feature/amazing-feature`)
3. Commit your changes (`git commit -m 'Add amazing feature'`)
4. Push to the branch (`git push origin feature/amazing-feature`)
5. Open a Pull Request

## 📄 License

This project is licensed under the MIT License - see the LICENSE file for details.

## 🙏 Acknowledgments

- [PokéAPI](https://pokeapi.co/) - Pokemon data API
- [Spring Boot](https://spring.io/projects/spring-boot) - Java framework
- [React](https://react.dev/) - Frontend library
- [Vite](https://vitejs.dev/) - Frontend build tool

## 👤 Author

**Sibghatullah**
- Email: sibghatullah366@gmail.com
- GitHub: [@sibu876](https://github.com/sibu876)

## 📞 Support

For support, email sibghatullah366@gmail.com or open an issue on GitHub.

---

**Made with ❤️ by Sibghatullah**
