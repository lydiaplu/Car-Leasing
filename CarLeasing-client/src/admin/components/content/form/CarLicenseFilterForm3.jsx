import React, { useEffect, useMemo, useReducer, useState } from 'react'
import { adminConfig } from '../../../../config/adminConfig';
import { buildCarCascadeTree } from '../../../../lib/tree/carCascadeTree';
import { getAllCarsByGroup } from '../../../../api/carApi'

const initialState = {
    carForm: {
        carBrand: "",
        model: "",
        carType: "",
        year: "",
        color: "",
        licensePlate: ""
    },
    treeNode: {
        rootNode: null,
        carBrandNode: null,
        modelNode: null,
        carTypeNode: null,
        yearNode: null,
        colorNode: null,
        licensePlateNode: null
    }
}

const reducer = (state, action) => {
    switch (action.type) {
        case 'SET_TREE':
            return { ...state, treeNode: { ...state.treeNode, rootNode: action.payload } };
        case 'UPDATE_FIELD':
            {
                const { name, value } = action.payload;
                let newState = { ...state, carForm: { ...state.carForm, [name]: value } };
                return updateCascade(newState, name);
            }
        default:
            return state;
    }
}

function updateCascade(state, fieldName) {
    let { treeNode, carForm } = state;

    function resetFields(fromField) {
        const fieldOrder = Object.keys(carForm);
        const startReset = fieldOrder.indexOf(fromField) + 1;

        for (let i = startReset; i < fieldOrder.length; i++) {
            carForm[fieldOrder[i]] = "";
        }

        for (let i = startReset; i < fieldOrder.length; i++) {
            const treeNodeKey = fieldOrder[i] + "Node";
            treeNode[treeNodeKey] = null;
        }
    }

    switch (fieldName) {
        case 'carBrand':
            // carForm.model = "";
            treeNode.carBrandNode = treeNode.rootNode.children[carForm.carBrand];
            resetFields(fieldName);
            break;
        case 'model':
            // carForm.carType = "";
            treeNode.modelNode = treeNode.carBrandNode.children[carForm.model];
            resetFields(fieldName);
            break;
        case 'carType':
            // carForm.year = "";
            treeNode.carTypeNode = treeNode.modelNode.children[carForm.carType];
            resetFields(fieldName);
            break;
        case 'year':
            // carForm.color = "";
            treeNode.yearNode = treeNode.carTypeNode.children[carForm.year];
            resetFields(fieldName);
            break;
        case 'color':
            // carForm.licensePlate = "";
            treeNode.colorNode = treeNode.yearNode.children[carForm.color];
            resetFields(fieldName);
            break;
    }

    return { ...state, treeNode, carForm };
}

export default function CarLicenseFilterForm3({ formState, formData, serCarId }) {

    const [state, dispatch] = useReducer(reducer, initialState);

    useEffect(() => {
        const fetchAllCars = async () => {
            try {
                const result = await getAllCarsByGroup();
                console.log("fetch all cars: ", result);

                if (result) {
                    const rootNode = buildCarCascadeTree(result.map(item => ({
                        id: item.id,
                        carBrand: item.carBrand,
                        model: item.model,
                        carType: item.carType,
                        year: item.year,
                        color: item.color,
                        licensePlate: item.licensePlate
                    })))
                    dispatch({ type: "SET_TREE", payload: rootNode });
                }
            } catch (error) {
                console.error('Failed to fetch cars:', error);
            }
        }
        fetchAllCars();
    }, []);

    useEffect(() => {
        if (formData.car && state.treeNode.rootNode) {
            dispatch({ type: "UPDATE_FIELD", payload: { name: "carBrand", value: formData.car.carBrand.id } });
            dispatch({ type: "UPDATE_FIELD", payload: { name: "model", value: formData.car.model } });
            dispatch({ type: "UPDATE_FIELD", payload: { name: "carType", value: formData.car.carType.id } });
            dispatch({ type: "UPDATE_FIELD", payload: { name: "year", value: formData.car.year } });
            dispatch({ type: "UPDATE_FIELD", payload: { name: "color", value: formData.car.color } });
            dispatch({ type: "UPDATE_FIELD", payload: { name: "licensePlate", value: formData.car.id } });

        } else if (!formData.car && state.treeNode.rootNode) {
            dispatch({ type: "UPDATE_FIELD", payload: { name: "carBrand", value: "" } });
            console.log("After form reset: ", formData)
        }
    }, [formData.car, state.treeNode.rootNode])

    const handleInputChange = (event) => {
        if (formState === adminConfig.formState.view) return;

        const { name, value } = event.target;
        dispatch({ type: "UPDATE_FIELD", payload: { name, value } });

        if (name === "licensePlate") {
            serCarId({ ...formData, carId: value })
        }
    }

    const renderOptions = (treeNode) => {
        return treeNode ? Object.values(treeNode.children).map(item => (
            <option key={item.value} value={item.value}>{item.name}</option>
        )) : null;
    }

    return (
        <>
            {/* Brand Name */}
            <div className="col-md-6">
                <label htmlFor="carBrand" className="form-label">
                    Brand Name
                </label>
                <select
                    className="form-select"
                    id="carBrand"
                    name="carBrand"
                    value={state.carForm.carBrand}
                    onChange={handleInputChange}
                    required
                >
                    <option value="">Choose...</option>
                    {renderOptions(state.treeNode.rootNode)}
                </select>
                <div className="valid-feedback"></div>
            </div>

            {/* Model */}
            <div className="col-md-6">
                <label htmlFor="model" className="form-label">Model</label>
                <select
                    className="form-select"
                    id="model"
                    name="model"
                    value={state.carForm.model}
                    onChange={handleInputChange}
                    required
                >
                    <option value="">Choose...</option>
                    {renderOptions(state.treeNode.carBrandNode)}
                </select>
                <div className="valid-feedback"></div>
            </div>

            {/* Car Type */}
            <div className="col-md-6">
                <label htmlFor="carType" className="form-label">
                    Car Type
                </label>
                <select
                    className="form-select"
                    id="carType"
                    name="carType"
                    value={state.carForm.carType}
                    onChange={handleInputChange}
                    required
                >
                    <option value="">Choose...</option>
                    {renderOptions(state.treeNode.modelNode)}
                </select>
                <div className="valid-feedback"></div>
            </div>

            {/* Year */}
            <div className="col-md-6">
                <label htmlFor="year" className="form-label">Year</label>
                <select
                    className="form-select"
                    id="year"
                    name="year"
                    value={state.carForm.year}
                    onChange={handleInputChange}
                    required
                >
                    <option value="">Choose...</option>
                    {renderOptions(state.treeNode.carTypeNode)}
                </select>
                <div className="valid-feedback"></div>
            </div>

            {/* Color */}
            <div className="col-md-6">
                <label htmlFor="color" className="form-label">Color</label>
                <select
                    className="form-select"
                    id="color"
                    name="color"
                    value={state.carForm.color}
                    onChange={handleInputChange}
                >
                    <option value="">Choose...</option>
                    {renderOptions(state.treeNode.yearNode)}
                </select>
                <div className="valid-feedback"></div>
            </div>

            {/* License Plate */}
            <div className="col-md-6">
                <label htmlFor="licensePlate" className="form-label">License Plate</label>
                <select
                    className="form-select"
                    id="licensePlate"
                    name="licensePlate"
                    value={state.carForm.licensePlate}
                    onChange={handleInputChange}
                    required
                >
                    <option value="">Choose...</option>
                    {renderOptions(state.treeNode.colorNode)}
                </select>
                <div className="valid-feedback"></div>
            </div>
        </>
    )
}
