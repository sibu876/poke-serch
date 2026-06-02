import styles from './TypeBadge.module.css'

const TYPE_COLORS = {
  fire: '#ff6b35',
  water: '#4a90e2',
  grass: '#4caf50',
  electric: '#f9c846',
  psychic: '#e91e8c',
  ice: '#74d7e8',
  dragon: '#6c3dbf',
  dark: '#5a4f6a',
  fairy: '#f48fb1',
  normal: '#9e9e9e',
  fighting: '#c62828',
  flying: '#80d8ff',
  poison: '#9c27b0',
  ground: '#d4a84b',
  rock: '#8d8d5e',
  bug: '#8bc34a',
  ghost: '#5e35b1',
  steel: '#b0bec5',
  unknown: '#808080',
  shadow: '#2c2c3e',
}

export default function TypeBadge({ type, large = false }) {
  const color = TYPE_COLORS[type] || '#808080'
  return (
    <span
      className={`${styles.badge} ${large ? styles.large : ''}`}
      style={{ background: color + '28', color, border: `1px solid ${color}55` }}
    >
      {type}
    </span>
  )
}
