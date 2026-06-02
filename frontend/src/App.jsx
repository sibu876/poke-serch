import { Routes, Route } from 'react-router-dom'
import Home from './pages/Home'
import PokemonPage from './pages/PokemonPage'
import Navbar from './components/Navbar'

export default function App() {
  return (
    <>
      <Navbar />
      <Routes>
        <Route path="/" element={<Home />} />
        <Route path="/pokemon/:nameOrId" element={<PokemonPage />} />
      </Routes>
    </>
  )
}
