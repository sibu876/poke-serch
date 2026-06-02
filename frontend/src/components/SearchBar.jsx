import { useState, useRef } from 'react'
import { useNavigate } from 'react-router-dom'
import styles from './SearchBar.module.css'

export default function SearchBar({ onSearch, placeholder = 'Search Pokémon...', showGoButton = false }) {
  const [query, setQuery] = useState('')
  const [focused, setFocused] = useState(false)
  const navigate = useNavigate()
  const inputRef = useRef(null)

  const handleSubmit = (e) => {
    e.preventDefault()
    const trimmed = query.trim()
    if (!trimmed) return
    if (showGoButton) {
      navigate(`/pokemon/${encodeURIComponent(trimmed.toLowerCase())}`)
    } else if (onSearch) {
      onSearch(trimmed)
    }
  }

  return (
    <form
      className={`${styles.form} ${focused ? styles.focused : ''}`}
      onSubmit={handleSubmit}
    >
      <svg className={styles.icon} viewBox="0 0 24 24" fill="none" stroke="currentColor" strokeWidth="2">
        <circle cx="11" cy="11" r="8" />
        <line x1="21" y1="21" x2="16.65" y2="16.65" />
      </svg>
      <input
        ref={inputRef}
        className={styles.input}
        type="text"
        value={query}
        onChange={(e) => setQuery(e.target.value)}
        onFocus={() => setFocused(true)}
        onBlur={() => setFocused(false)}
        placeholder={placeholder}
        autoComplete="off"
        spellCheck={false}
      />
      {query && (
        <button
          type="button"
          className={styles.clear}
          onClick={() => { setQuery(''); inputRef.current?.focus() }}
          aria-label="Clear"
        >
          ×
        </button>
      )}
      {showGoButton && (
        <button type="submit" className={styles.goBtn}>
          Search
        </button>
      )}
    </form>
  )
}
