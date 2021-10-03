import { Service } from './service'
import { CreateService } from './createService'
import { useState, useEffect } from 'react'
import { useInterval } from '../hooks'
import { API_HOST } from '../config'

function ServiceContainer() {
  let [services, setServices] = useState([])

  useInterval(() => {
    console.log('Polling new data')
    fetchServices(setServices)
  }, 10000)

  useEffect(() => {
    fetchServices(setServices)
  }, [])

  const deleteService = (serviceId) => {
    setServices(services.filter((service) => service.id !== serviceId))
  }

  return (
    <div style={serviceContainerStyle}>
      <div style={serviceListStyle}>
        {services.map((service) => {
          return (
            <Service
              key={service.id}
              service={service}
              onServiceDeleted={(serviceId) => {
                deleteService(serviceId)
              }}
            />
          )
        })}
      </div>

      <div>
        <CreateService
          onServiceCreated={(service) => {
            setServices([...services, service])
          }}
        />
      </div>
    </div>
  )
}

const fetchServices = (setServices) => {
  fetch(API_HOST + '/service')
    .then((response) => response.json())
    .then((data) => setServices(data))
}

const serviceContainerStyle = {
  display: 'flex',
  flexDirection: 'column',
}

const serviceListStyle = {
  display: 'flex',
  flexWrap: 'wrap',
}

export { ServiceContainer }
