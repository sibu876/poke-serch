# Pokédex Search Engine

A full-stack Pokédex app with a **React + Vite** frontend and a **Spring Boot** backend. The backend proxies and caches calls to the [PokéAPI](https://pokeapi.co/).

```
pokedex/
├── frontend/    ← React + Vite (port 5173)
└── backend/     ← Spring Boot (port 8080)
```

---

## Prerequisites

| Tool | Version |
|------|---------|
| Node.js | 18+ |
| npm | 9+ |
| Java | 17+ |
| Maven | 3.8+ (or use the wrapper `./mvnw`) |

---

## Running Locally (VS Code)

### 1 — Start the Backend

```bash
cd backend
./mvnw spring-boot:run
```

Or if you have Maven installed globally:

```bash
cd backend
mvn spring-boot:run
```

The API server starts on **http://localhost:8080**.

> First run downloads Maven dependencies — this takes ~1 minute. Subsequent runs are instant.

### 2 — Start the Frontend

Open a **second terminal**:

```bash
cd frontend
npm install
npm run dev
```

The frontend starts on **http://localhost:5173** and proxies all `/api` calls to the backend automatically.

### 3 — Open the App

Visit **http://localhost:5173** in your browser.

---

## API Endpoints

All endpoints are served under `http://localhost:8080/api/v1/pokemon`.

| Method | Path | Description |
|--------|------|-------------|
| `GET` | `/api/v1/pokemon` | First 20 Pokémon (home page) |
| `GET` | `/api/v1/pokemon/{nameOrId}` | Full detail by name or numeric ID |
| `GET` | `/api/v1/pokemon/search?q=bulba&limit=20` | Search by partial name |

**Example requests:**

```bash
# Get Pikachu
curl http://localhost:8080/api/v1/pokemon/pikachu

# Get by ID
curl http://localhost:8080/api/v1/pokemon/25

# Search
curl "http://localhost:8080/api/v1/pokemon/search?q=char&limit=10"
```

---

## Caching

The backend uses **Caffeine** (in-memory) via Spring Cache:

| Setting | Value |
|---------|-------|
| Max entries | 500 |
| TTL (time-to-live) | 30 minutes |

Cache config is in `backend/src/main/java/com/pokedex/config/CacheConfig.java`.

---

## Backend Architecture

```
backend/src/main/java/com/pokedex/
├── PokedexApplication.java          ← Spring Boot entry point
├── config/
│   ├── CacheConfig.java             ← Caffeine cache (max size, TTL)
│   ├── CorsConfig.java              ← CORS for localhost:5173
│   └── WebClientConfig.java         ← WebClient for PokeAPI
├── controller/
│   └── PokemonController.java       ← REST endpoints
├── service/
│   ├── PokemonService.java          ← Interface
│   └── impl/
│       └── PokemonServiceImpl.java  ← Caching + PokeAPI calls
├── dto/
│   ├── PokemonResponseDto.java      ← Full Pokémon detail response
│   ├── PokemonSummaryDto.java       ← Lightweight card response
│   ├── ApiErrorResponse.java        ← Unified error shape
│   ├── PokeApiPokemonResponse.java  ← PokeAPI raw response mapping
│   ├── PokeApiListResponse.java     ← PokeAPI list response
│   ├── PokemonTypeDto.java
│   ├── PokemonStatDto.java
│   ├── PokemonAbilityDto.java
│   ├── PokemonMoveDto.java
│   └── PokemonSpritesDto.java
└── exception/
    ├── PokemonNotFoundException.java
    ├── PokeApiException.java
    └── GlobalExceptionHandler.java  ← Unified @RestControllerAdvice
```

---

## Frontend Architecture

```
frontend/src/
├── main.jsx                ← React root
├── App.jsx                 ← Router setup
├── index.css               ← Global CSS variables + reset
├── services/
│   └── api.js              ← Axios client for all API calls
├── components/
│   ├── Navbar.jsx/css
│   ├── SearchBar.jsx/css
│   ├── PokemonCard.jsx/css ← Grid card (summary)
│   ├── TypeBadge.jsx/css   ← Coloured type pill
│   └── StatsBar.jsx/css    ← Animated stat bars
└── pages/
    ├── Home.jsx/css        ← Search + grid
    └── PokemonPage.jsx/css ← Full detail + shiny toggle
```

---

## Pushing to GitHub

```bash
# From the project root (one level above frontend/ and backend/)
git init
git add .
git commit -m "Initial commit: Pokédex search engine"

# Create a repo on github.com, then:
git remote add origin https://github.com/<your-username>/pokedex.git
git branch -M main
git push -u origin main
```

---

## Build for Production

**Backend JAR:**

```bash
cd backend
mvn clean package -DskipTests
java -jar target/pokedex-backend-1.0.0.jar
```

**Frontend static build:**

```bash
cd frontend
npm run build
# Output in frontend/dist/
```
