const validateServiceName = (serviceName) => {
  if (serviceName === '') {
    return validationResponse(true, 'Should not be empty')
  }
  return validationResponse(false)
}

const validateServiceUrl = (serviceUrl) => {
  if (!serviceUrl.startsWith('http://') && !serviceUrl.startsWith('https://')) {
    return validationResponse(true, 'Invalid URL')
  }
  return validationResponse(false)
}

const validationResponse = (failed, message) => {
  return {
    failed,
    message,
  }
}

export { validateServiceName, validateServiceUrl }
