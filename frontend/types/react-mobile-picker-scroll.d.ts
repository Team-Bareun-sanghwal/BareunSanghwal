declare module 'react-mobile-picker-scroll' {
  import React from 'react';

  export interface IOptionType {
    [key: string]: any;
  }

  export interface IValueType {
    [key: string]: any;
  }

  export interface IPickerProps {
    optionGroups: IOptionType;
    valueGroups: IValueType;
    onChange: (name: string, value: any) => void;
  }

  export class Picker extends React.Component<IPickerProps> {}

  export default Picker;
}
