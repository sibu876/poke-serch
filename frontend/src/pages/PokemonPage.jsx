import { useEffect, useState } from 'react'
import { useParams, useNavigate } from 'react-router-dom'
import { getPokemon } from '../services/api'
import TypeBadge from '../components/TypeBadge'
import StatsBar from '../components/StatsBar'
import SearchBar from '../components/SearchBar'
import styles from './PokemonPage.module.css'

export default function PokemonPage() {
  const { nameOrId } = useParams()
  const navigate = useNavigate()
  const [pokemon, setPokemon] = useState(null)
  const [loading, setLoading] = useState(true)
  const [error, setError] = useState(null)
  const [shiny, setShiny] = useState(false)

  useEffect(() => {
    setLoading(true)
    setError(null)
    setPokemon(null)
    setShiny(false)
    getPokemon(nameOrId)
      .then(setPokemon)
      .catch((err) => setError(err.message))
      .finally(() => setLoading(false))
  }, [nameOrId])

  const handleSearch = (query) => {
    navigate(`/pokemon/${encodeURIComponent(query.trim().toLowerCase())}`)
  }

  const activeImage = shiny
    ? (pokemon?.sprites?.frontShiny || pokemon?.sprites?.officialArtworkShiny || pokemon?.imageUrl)
    : (pokemon?.sprites?.officialArtwork || pokemon?.imageUrl)

  return (
    <main className={styles.main}>
      <div className={styles.topBar}>
        <button className={styles.back} onClick={() => navigate(-1)}>
          ← Back
        </button>
        <SearchBar
          onSearch={handleSearch}
          placeholder="Search another Pokémon…"
          showGoButton
        />
      </div>

      {loading && (
        <div className={styles.loadingWrap}>
          <div className={styles.spinner} />
          <span>Loading…</span>
        </div>
      )}

      {error && (
        <div className={styles.error}>
          <span>{error}</span>
          <button className={styles.retryBtn} onClick={() => navigate('/')}>
            Back to home
          </button>
        </div>
      )}

      {pokemon && (
        <div className={styles.card}>
          <aside className={styles.left}>
            <span className={styles.number}>#{String(pokemon.id).padStart(3, '0')}</span>
            <div className={styles.imageWrap}>
              <img
                key={shiny ? 'shiny' : 'normal'}
                src={activeImage}
                alt={pokemon.name}
                className={styles.image}
              />
            </div>
            <div className={styles.shinyToggle}>
              <button
                className={`${styles.shinyBtn} ${shiny ? styles.active : ''}`}
                onClick={() => setShiny((s) => !s)}
              >
                ✨ {shiny ? 'Shiny' : 'Normal'}
              </button>
            </div>
            <div className={styles.sprites}>
              {[
                { label: 'Front', src: pokemon.sprites?.frontDefault },
                { label: 'Back', src: pokemon.sprites?.backDefault },
              ]
                .filter((s) => s.src)
                .map((s) => (
                  <div key={s.label} className={styles.spriteItem}>
                    <img src={s.src} alt={s.label} className={styles.sprite} />
                    <span>{s.label}</span>
                  </div>
                ))}
            </div>
          </aside>

          <section className={styles.right}>
            <h1 className={styles.name}>{pokemon.name}</h1>

            <div className={styles.types}>
              {pokemon.types.map((t) => (
                <TypeBadge key={t} type={t} large />
              ))}
            </div>

            <div className={styles.vitals}>
              <div className={styles.vital}>
                <span className={styles.vitalLabel}>Height</span>
                <span className={styles.vitalValue}>{(pokemon.height / 10).toFixed(1)} m</span>
              </div>
              <div className={styles.vital}>
                <span className={styles.vitalLabel}>Weight</span>
                <span className={styles.vitalValue}>{(pokemon.weight / 10).toFixed(1)} kg</span>
              </div>
              {pokemon.baseExperience && (
                <div className={styles.vital}>
                  <span className={styles.vitalLabel}>Base XP</span>
                  <span className={styles.vitalValue}>{pokemon.baseExperience}</span>
                </div>
              )}
            </div>

            <div className={styles.section}>
              <h2 className={styles.sectionTitle}>Base Stats</h2>
              <StatsBar stats={pokemon.stats} />
            </div>

            <div className={styles.section}>
              <h2 className={styles.sectionTitle}>Abilities</h2>
              <div className={styles.abilities}>
                {pokemon.abilities.map((a) => (
                  <span key={a.name} className={`${styles.ability} ${a.hidden ? styles.hidden : ''}`}>
                    {a.name}
                    {a.hidden && <em> (hidden)</em>}
                  </span>
                ))}
              </div>
            </div>

            <div className={styles.section}>
              <h2 className={styles.sectionTitle}>Moves <span className={styles.movesCount}>(first 20)</span></h2>
              <div className={styles.moves}>
                {pokemon.moves.map((m) => (
                  <span key={m} className={styles.move}>{m}</span>
                ))}
              </div>
            </div>
          </section>
        </div>
      )}
    </main>
  )
}
