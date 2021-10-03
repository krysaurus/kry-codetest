import { useState } from 'react'
import { KRY_BUTTON_GREEN, SERVICE_STATUS_FAIL_RED } from '../util/colors'
import { validateServiceName, validateServiceUrl } from '../validation'
import { API_HOST } from '../config'

const DEFAULT_SERVICE_NAME = ''
const DEFAULT_SERVICE_URL = ''

function CreateService({ onServiceCreated }) {
  let [serviceName, setServiceName] = useState(DEFAULT_SERVICE_NAME)
  let [serviceNameValidationFailed, setServiceNameValidationFailed] =
    useState(false)

  let [serviceUrl, setServiceUrl] = useState(DEFAULT_SERVICE_URL)
  let [serviceUrlValidationFailed, setServiceUrlValidationFailed] =
    useState(false)

  const resetInput = () => {
    setServiceName(DEFAULT_SERVICE_NAME)
    setServiceUrl(DEFAULT_SERVICE_URL)
  }

  const resetValidation = () => {
    setServiceUrlValidationFailed(false)
    setServiceNameValidationFailed(false)
  }

  const onChangeName = (e) => {
    setServiceName(e.target.value)
  }

  const onChangeUrl = (e) => {
    setServiceUrl(e.target.value)
  }

  const submitService = () => {
    resetValidation()

    const serviceNameValidation = validateServiceName(serviceName)
    const serviceUrlValidation = validateServiceUrl(serviceUrl)
    const validService = !(
      serviceNameValidation.failed || serviceUrlValidation.failed
    )

    setServiceNameValidationFailed(serviceNameValidation.failed)
    setServiceUrlValidationFailed(serviceUrlValidation.failed)

    if (validService) {
      resetInput()
      fetch(API_HOST + '/service', {
        method: 'POST',
        headers: {
          'Content-Type': 'application/json',
        },
        body: JSON.stringify({
          name: serviceName,
          url: serviceUrl,
        }),
      })
        .then((res) => res.json())
        .then((data) => {
          onServiceCreated(data)
        })
        .catch((e) => {
          console.error('Something went horribly wrong')
          console.error(e)
        })
    }
  }

  return (
    <div style={serviceContainerStyle} className="serviceText">
      <div style={createNewServiceLabelStyle}>
        <span>Create new Service</span>
      </div>

      <div style={wrapperStyle} className="wrapper">
        <span>Name</span>
        <div>
          <input
            style={createServiceInputStyle}
            value={serviceName}
            onChange={(e) => onChangeName(e, setServiceName)}
          />
        </div>
        {serviceNameValidationFailed && (
          <div style={createServiceValidationErrorStyle}>
            Should not be empty
          </div>
        )}

        <span>Url</span>
        <div>
          <input
            style={createServiceInputStyle}
            placeholder="http(s)://"
            value={serviceUrl}
            onChange={(e) => onChangeUrl(e, setServiceUrl)}
          />
        </div>
        {serviceUrlValidationFailed && (
          <div style={createServiceValidationErrorStyle}>Invalid URL</div>
        )}
      </div>

      <div style={createServiceSubmitContainerStyle}>
        <button
          style={createServiceSubmitStyle}
          type="Submit"
          onClick={() => {
            submitService(
              serviceName,
              serviceUrl,
              setServiceNameValidationFailed,
              setServiceUrlValidationFailed
            )
          }}
        >
          Submit
        </button>
      </div>
    </div>
  )
}

const serviceContainerStyle = {
  display: 'flex',
  flexDirection: 'column',
  backgroundColor: 'white',
  paddingTop: '2rem',
  paddingBottom: '3rem',
  marginTop: '6rem',
  marginLeft: '20rem',
  marginRight: '20rem',
  borderRadius: '2rem',
  fontSize: '2rem',
}

const createServiceInputStyle = {
  paddingLeft: '1.5rem',
  paddingTop: '1rem',
  paddingBottom: '1rem',
  borderRadius: '2rem',
  fontSize: '1.6rem',
  outline: 'none',
}

const createNewServiceLabelStyle = {
  marginBottom: '3rem',
  fontSize: '4rem',
}

const createServiceSubmitContainerStyle = {
  justifyContent: 'center',
}

const createServiceSubmitStyle = {
  marginTop: '1rem',
  borderRadius: '2rem',
  fontSize: '1.5rem',
  paddingTop: '1rem',
  paddingLeft: '4rem',
  paddingRight: '4rem',
  paddingBottom: '1rem',
  cursor: 'pointer',
  color: 'white',
  backgroundColor: KRY_BUTTON_GREEN,
}

const wrapperStyle = {
  margin: '0 auto',
}

const createServiceValidationErrorStyle = {
  textAlign: 'left',
  fontSize: '1.2rem',
  color: SERVICE_STATUS_FAIL_RED,
}

export { CreateService }
