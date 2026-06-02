import axios from 'axios'

const BASE_URL = '/api/v1/pokemon'

const client = axios.create({ baseURL: BASE_URL })

client.interceptors.response.use(
  (res) => res,
  (err) => {
    const message =
      err.response?.data?.message || err.message || 'Something went wrong'
    return Promise.reject(new Error(message))
  }
)

export const getPokemon = (nameOrId) =>
  client.get(`/${encodeURIComponent(nameOrId)}`).then((r) => r.data)

export const searchPokemon = (query, limit = 20) =>
  client.get('/search', { params: { q: query, limit } }).then((r) => r.data)

export const getPopularPokemon = (limit = 20) =>
  client.get('', { params: { limit } }).then((r) => r.data)
