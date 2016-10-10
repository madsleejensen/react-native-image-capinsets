import React, { Component } from 'react';
import {
  View,
  Image,
  requireNativeComponent,
} from 'react-native';
import resolveAssetSource from 'react-native/Libraries/Image/resolveAssetSource';

class ImageCapInset extends Component {
  render() {
    const normalizedSource = resolveAssetSource(this.props.source);

    return (
      <RCTImageCapInset
        {...this.props}
        source={normalizedSource}
        resizeMode={Image.resizeMode.stretch}
      />
    );
  }
}

ImageCapInset.propTypes = {
  ...View.propTypes,
  source: Image.propTypes.source,
  capInsets: React.PropTypes.shape({
    top: React.PropTypes.number,
    left: React.PropTypes.number,
    right: React.PropTypes.number,
    bottom: React.PropTypes.number,
  }),
};

const RCTImageCapInset = requireNativeComponent('RCTImageCapInset', {
  propTypes: ImageCapInset.propTypes,
});

export default ImageCapInset;
