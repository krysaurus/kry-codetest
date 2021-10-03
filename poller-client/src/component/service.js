import { DeleteService } from './deleteService'
import { humanDate, humanTime } from '../util/util'
import {
  KRY_BUTTON_GREEN,
  SERVICE_STATUS_OK_GREEN,
  SERVICE_STATUS_FAIL_RED,
  WHITE,
} from '../util/colors'

function Service({ service, onServiceDeleted }) {
  if (!service) {
    return null
  }

  return (
    <div style={{ ...serviceStyle }} className="serviceText">
      <span style={serviceNameStyle} className="serviceText">
        {service.name}
      </span>
      <span style={serviceUrlStyle}>
        <a href={service.url} style={serviceUrlStyle}>
          {service.url}
        </a>
      </span>
      {service.status === 'OK' ? renderOKStatus() : renderFailStatus(service)}
      <span style={serviceUpdatedAtStyle}>
        since {humanDate(service.updatedAt)} - {humanTime(service.updatedAt)}
      </span>
      <DeleteService service={service} onServiceDeleted={onServiceDeleted} />
      <span style={serviceUpdatedAtStyle}>
        Created: {humanDate(service.createdAt)} - {humanTime(service.createdAt)}
      </span>
    </div>
  )
}

const renderOKStatus = () => {
  return (
    <span style={serviceStatusOkStyle} className="serviceContainer">
      OK
    </span>
  )
}

const renderFailStatus = (service) => {
  return (
    <span style={serviceStatusFailedStyle} className="serviceContainer">
      {service.status}
    </span>
  )
}

const serviceStyle = {
  display: 'flex',
  flex: '1 0 15%',
  flexDirection: 'column',
  backgroundColor: WHITE,
  borderRadius: '1rem',
  fontSize: '2rem',
  marginTop: '1rem',
  marginLeft: '1rem',
  marginRight: '1rem',
  paddingLeft: '4rem',
  paddingRight: '4rem',
  paddingBottom: '3rem',
}

const serviceNameStyle = {
  paddingTop: '1rem',
  fontFamily: 'Montserrat sans-serif',
  fontSize: '4rem',
}

const serviceUrlStyle = {
  color: KRY_BUTTON_GREEN,
  marginBottom: '4rem ',
  paddingTop: '1rem',
  fontSize: '1.6rem',
}

const serviceStatusOkStyle = {
  color: SERVICE_STATUS_OK_GREEN,
}

const serviceStatusFailedStyle = {
  color: SERVICE_STATUS_FAIL_RED,
}

const serviceUpdatedAtStyle = {
  marginTop: '0.5rem',
  fontSize: '1.3rem',
}

export { Service }
