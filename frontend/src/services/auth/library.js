import axios from "axios";

const instance = axios.create({
  baseURL: "http://localhost:8090/library",
  headers: {
    "Content-Type": "application/json",
    // authHeader
  },
});

export default instance;