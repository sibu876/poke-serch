import { useNavigate } from 'react-router-dom'
import TypeBadge from './TypeBadge'
import styles from './PokemonCard.module.css'

export default function PokemonCard({ pokemon }) {
  const navigate = useNavigate()

  return (
    <div
      className={styles.card}
      onClick={() => navigate(`/pokemon/${pokemon.name}`)}
      role="button"
      tabIndex={0}
      onKeyDown={(e) => e.key === 'Enter' && navigate(`/pokemon/${pokemon.name}`)}
    >
      <span className={styles.number}>#{String(pokemon.id).padStart(3, '0')}</span>
      <div className={styles.imageWrap}>
        {pokemon.imageUrl ? (
          <img
            src={pokemon.imageUrl}
            alt={pokemon.name}
            className={styles.image}
            loading="lazy"
          />
        ) : (
          <div className={styles.placeholder}>?</div>
        )}
      </div>
      <h3 className={styles.name}>{pokemon.name}</h3>
      <div className={styles.types}>
        {pokemon.types.map((t) => (
          <TypeBadge key={t} type={t} />
        ))}
      </div>
    </div>
  )
}
