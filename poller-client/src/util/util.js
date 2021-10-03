const humanDate = (timeStamp) => {
  return new Date(timeStamp).toLocaleDateString()
}

const humanTime = (timeStamp) => {
  return new Date(timeStamp).toLocaleTimeString()
}

export { humanDate, humanTime }
