import { useState, useEffect, useCallback } from 'react'
import { useNavigate } from 'react-router-dom'
import SearchBar from '../components/SearchBar'
import PokemonCard from '../components/PokemonCard'
import { getPopularPokemon, searchPokemon } from '../services/api'
import styles from './Home.module.css'

export default function Home() {
  const [pokemon, setPokemon] = useState([])
  const [loading, setLoading] = useState(true)
  const [searching, setSearching] = useState(false)
  const [error, setError] = useState(null)
  const [searchQuery, setSearchQuery] = useState('')
  const navigate = useNavigate()

  const loadPopular = useCallback(async () => {
    setLoading(true)
    setError(null)
    try {
      const data = await getPopularPokemon(20)
      setPokemon(data)
    } catch (err) {
      setError(err.message)
    } finally {
      setLoading(false)
    }
  }, [])

  useEffect(() => { loadPopular() }, [loadPopular])

  const handleSearch = async (query) => {
    setSearchQuery(query)
    setSearching(true)
    setError(null)
    try {
      const trimmed = query.trim().toLowerCase()
      if (/^\d+$/.test(trimmed)) {
        navigate(`/pokemon/${trimmed}`)
        return
      }
      const data = await searchPokemon(trimmed, 40)
      setPokemon(data)
    } catch (err) {
      setError(err.message)
      setPokemon([])
    } finally {
      setSearching(false)
    }
  }

  const handleClearSearch = () => {
    setSearchQuery('')
    loadPopular()
  }

  const isFiltered = Boolean(searchQuery)

  return (
    <main className={styles.main}>
      <header className={styles.header}>
        <h1 className={styles.title}>
          <span className={styles.accent}>Pokémon</span> Search
        </h1>
        <p className={styles.subtitle}>
          Search by name or ID to explore the world of Pokémon
        </p>
        <div className={styles.searchWrap}>
          <SearchBar onSearch={handleSearch} placeholder="Search by name or ID…" />
        </div>
        {isFiltered && (
          <button className={styles.clearBtn} onClick={handleClearSearch}>
            ← Back to all Pokémon
          </button>
        )}
      </header>

      {error && (
        <div className={styles.error}>
          {error}
          <button onClick={loadPopular} className={styles.retryBtn}>Retry</button>
        </div>
      )}

      <section className={styles.section}>
        <h2 className={styles.sectionTitle}>
          {isFiltered
            ? `Results for "${searchQuery}" (${pokemon.length})`
            : 'First Generation'}
        </h2>

        {(loading || searching) ? (
          <div className={styles.grid}>
            {Array.from({ length: 20 }).map((_, i) => (
              <div key={i} className={styles.skeleton} />
            ))}
          </div>
        ) : pokemon.length === 0 ? (
          <div className={styles.empty}>
            <span>No Pokémon found for "{searchQuery}"</span>
            <button className={styles.clearBtn} onClick={handleClearSearch}>
              Clear search
            </button>
          </div>
        ) : (
          <div className={styles.grid}>
            {pokemon.map((p) => (
              <PokemonCard key={p.id} pokemon={p} />
            ))}
          </div>
        )}
      </section>
    </main>
  )
}
