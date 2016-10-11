import React, { Component } from 'react';
import {
  View,
  Image,
  requireNativeComponent,
} from 'react-native';
import resolveAssetSource from 'react-native/Libraries/Image/resolveAssetSource';

class ImageCapInset extends Component {
  render() {
    const {
      children,
      source,
      capInsets,
      ...rest
    } = this.props;

    const normalizedSource = resolveAssetSource(source);

    return (
      <View {...rest}>
        <RCTImageCapInset
          style={{position: 'absolute', top: 0, left: 0, bottom: 0, right: 0}}
          capInsets={capInsets}
          source={normalizedSource}
          resizeMode={Image.resizeMode.stretch}
        />
        {children}
      </View>
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
