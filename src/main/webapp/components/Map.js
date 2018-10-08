import React, { Component } from 'react';
import { withGoogleMap, GoogleMap } from 'react-google-maps';
class Map extends Component {
   render() {
   const GoogleMapExample = withGoogleMap(props => (
      <GoogleMap
        defaultCenter = { { lat: 40.756795, lng: -73.954298 } }
        defaultZoom = { 13 }
      >
      </GoogleMap>
   ));
   return(
      <div>
        <GoogleMapExample
         containerElement={
                             <div style={{    position: 'absolute',
                                 top: 0,
                                 left: 0,
                                 right: 0,
                                 bottom: 0,
                                 justifyContent: 'flex-end',
                                 alignItems: 'center',}} />
                         }
         mapElement={
                             <div style={{    position: 'absolute',
                                 top: 0,
                                 left: 0,
                                 right: 0,
                                 bottom: 0,
                            }} />
                         }
        />
      </div>
   );
   }
};
export default Map;