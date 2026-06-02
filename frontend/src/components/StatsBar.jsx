import styles from './StatsBar.module.css'

const STAT_LABELS = {
  hp: 'HP',
  attack: 'ATK',
  defense: 'DEF',
  'special-attack': 'SP.ATK',
  'special-defense': 'SP.DEF',
  speed: 'SPD',
}

const STAT_COLORS = {
  hp: '#ff6b6b',
  attack: '#ff9f43',
  defense: '#54a0ff',
  'special-attack': '#ff6b81',
  'special-defense': '#48dbfb',
  speed: '#1dd1a1',
}

export default function StatsBar({ stats }) {
  const maxStat = 255

  return (
    <div className={styles.container}>
      {stats.map((stat) => {
        const label = STAT_LABELS[stat.name] || stat.name
        const color = STAT_COLORS[stat.name] || '#9090b0'
        const pct = Math.min((stat.baseStat / maxStat) * 100, 100)
        return (
          <div key={stat.name} className={styles.row}>
            <span className={styles.label}>{label}</span>
            <span className={styles.value}>{stat.baseStat}</span>
            <div className={styles.track}>
              <div
                className={styles.fill}
                style={{ width: `${pct}%`, background: color }}
              />
            </div>
          </div>
        )
      })}
    </div>
  )
}
