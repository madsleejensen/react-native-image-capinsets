import React, { Component } from 'react';
import {
  ImageBackground,
  Image,
} from 'react-native';

class ImageCapInset extends Component {
  render() {
    return (
      <ImageBackground
        {...this.props}
        resizeMode={Image.resizeMode.stretch}
      />
    );
  }
}

export default ImageCapInset;
