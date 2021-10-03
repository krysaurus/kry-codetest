import { API_HOST } from '../config'
import { DELETE_RED, WHITE } from '../util/colors'

function DeleteService({ service, onServiceDeleted }) {
  const deleteService = () => {
    fetch(API_HOST+ '/service/' + service.id, {
      method: 'DELETE',
      headers: {
        'Content-Type': 'application/json',
      },
    })
      .then(() => {
        onServiceDeleted(service.id)
      })
      .catch((e) => {
        console.error('Something went horribly wrong')
        console.error(e)
      })
  }

  return (
    <div>
      <button
        style={deleteServiceStyle}
        type="Submit"
        onClick={() => {
          deleteService(service.id)
        }}
      >
        Delete
      </button>
    </div>
  )
}

const deleteServiceStyle = {
  marginTop: '1rem',
  borderRadius: '2rem',
  fontSize: '1.5rem',
  paddingTop: '1rem',
  paddingLeft: '4rem',
  paddingRight: '4rem',
  paddingBottom: '1rem',
  cursor: 'pointer',
  color: WHITE,
  backgroundColor: DELETE_RED,
}

export { DeleteService }
