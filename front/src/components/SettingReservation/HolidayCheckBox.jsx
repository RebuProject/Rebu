import React from "react";
import styled from "styled-components";

const CheckboxWrapper = styled.div`
  display: flex;
  align-items: center; /* Center the checkbox vertically */
  justify-content: center; /* Center the checkbox horizontally */
  height: 100%; /* Take the full height of the container */
  width: 100%; /* Ensure full width */
  text-align: center;
  --s-xsmall: 0.625em;
  --s-small: 1.2em;
  --border-width: 1px;
  --c-primary: #943aee;
  --c-primary-20-percent-opacity: rgba(95, 17, 232, 0.2);
  --c-primary-10-percent-opacity: rgba(95, 17, 232, 0.1);
  --t-base: 0.3s;
  --t-fast: 0.15s;
  --e-in: ease-in;
  --e-out: cubic-bezier(0.11, 0.29, 0.18, 0.98);
`;

const HiddenCheckbox = styled.input.attrs({ type: "checkbox" })`
  border: 0;
  clip: rect(0, 0, 0, 0);
  height: 1px;
  margin: -1px;
  overflow: hidden;
  padding: 0;
  position: absolute;
  width: 1px;
`;

const CheckboxLabel = styled.label`
  display: flex;
  align-items: center;
  justify-content: center; /* Center the checkbox content horizontally */
  cursor: pointer;
  margin: 0; /* Remove any default margins */
`;

const CheckboxSymbol = styled.span`
  display: flex;
  margin-right: calc(var(--s-small) * 0.7);
  border: var(--border-width) solid var(--c-primary);
  position: relative;
  border-radius: 0.1em;
  width: 1em;
  height: 1em;
  transition: box-shadow var(--t-base) var(--e-out),
    background-color var(--t-base);
  box-shadow: 0 0 0 0 var(--c-primary-10-percent-opacity);
  background-color: #fff;

  svg {
    width: 1em;
    height: 1em;
    margin: auto;
    fill: none;
    stroke-width: 3;
    stroke: currentColor;
    stroke-linecap: round;
    stroke-linejoin: round;
    stroke-miterlimit: 10;
    color: var(--c-primary);
    display: inline-block;

    path {
      transition: stroke-dashoffset var(--t-fast) var(--e-in);
      stroke-dasharray: 30px, 31px;
      stroke-dashoffset: ${(props) => (props.checked ? "0px" : "31px")};
    }
  }
`;

const Checkbox = ({ value, onChange }) => (
  <CheckboxWrapper>
    <CheckboxLabel>
      <HiddenCheckbox checked={value} onChange={onChange} />
      <CheckboxSymbol checked={value}>
        <svg
          aria-hidden="true"
          className="icon-checkbox"
          width="28px"
          height="28px"
          viewBox="0 0 28 28"
          version="1"
          xmlns="http://www.w3.org/2000/svg"
        >
          <path d="M4 14l8 7L24 7"></path>
        </svg>
      </CheckboxSymbol>
    </CheckboxLabel>
  </CheckboxWrapper>
);

export default Checkbox;
