import { Link } from 'react-router-dom'
import styles from './Navbar.module.css'

export default function Navbar() {
  return (
    <nav className={styles.nav}>
      <Link to="/" className={styles.brand}>
        <img
          src="https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/items/poke-ball.png"
          alt="pokeball"
          className={styles.logo}
        />
        <span>Pokédex</span>
      </Link>
    </nav>
  )
}
