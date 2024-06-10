import React from 'react'
import { Routes, Route } from 'react-router-dom'

import "./scss/adminlte.scss"

import MainLayout from './components/layout/MainLayout'

// car-brand
import CarBrandList from './components/carBrand/CarBrandList'
import AddCarBrand from './components/carBrand/AddCarBrand'
import EditCarBrand from './components/carBrand/EditCarBrand'
import ViewCarBrand from './components/carBrand/ViewCarBrand'
// car-type
import CarTypeList from './components/carType/CarTypeList'
import AddCarType from './components/carType/AddCarType'
import EditCarType from './components/carType/EditCarType'
import ViewCarType from './components/carType/ViewCarType'
// car
import CarList from './components/car/CarList'
import AddCar from './components/car/AddCar'
import EditCar from './components/car/EditCar'
import ViewCar from './components/car/ViewCar'
// car-maintenance
import CarMaintenanceList from './components/carMaintenance/CarMaintenanceList'
import AddCarMaintenance from './components/carMaintenance/AddCarMaintenance'
import EditCarMaintenance from './components/carMaintenance/EditCarMaintenance'
import ViewCarMaintenance from './components/carMaintenance/ViewCarMaintenance'
// car-insurance
import CarInsuranceList from './components/carInsurance/CarInsuranceList'
import AddCarInsurance from './components/carInsurance/AddCarInsurance'
import EditCarInsurance from './components/carInsurance/EditCarInsurance'
import ViewCarInsurance from './components/carInsurance/ViewCarInsurance'
// car-violation
import CarViolationList from './components/carViolation/CarViolationList'
import AddCarViolation from './components/carViolation/AddCarViolation'
import EditCarViolation from './components/carViolation/EditCarViolation'
import ViewCarViolation from './components/carViolation/ViewCarViolation'

function AdminApp() {
  return (
    <Routes>
      <Route path="/" element={<MainLayout />}>
        <Route path="carbrands" element={<CarBrandList />} />
        <Route path="carbrands/add" element={<AddCarBrand />} />
        <Route path="carbrands/edit/:carBrandId" element={<EditCarBrand />} />
        <Route path="carbrands/view/:carBrandId" element={<ViewCarBrand />} />

        <Route path="cartypes" element={<CarTypeList />} />
        <Route path="cartypes/add" element={<AddCarType />} />
        <Route path="cartypes/edit/:carTypeId" element={<EditCarType />} />
        <Route path="cartypes/view/:carTypeId" element={<ViewCarType />} />

        <Route path="cars" element={<CarList />} />
        <Route path="cars/add" element={<AddCar />} />
        <Route path="cars/edit/:carId" element={<EditCar />} />
        <Route path="cars/view/:carId" element={<ViewCar />} />

        <Route path="maintenances" element={<CarMaintenanceList />} />
        <Route path="maintenances/add" element={<AddCarMaintenance />} />
        <Route path="maintenances/edit/:maintenanceId" element={<EditCarMaintenance />} />
        <Route path="maintenances/view/:maintenanceId" element={<ViewCarMaintenance />} />

        <Route path="insurances" element={<CarInsuranceList />} />
        <Route path="insurances/add" element={<AddCarInsurance />} />
        <Route path="insurances/edit/:insuranceId" element={<EditCarInsurance />} />
        <Route path="insurances/view/:insuranceId" element={<ViewCarInsurance />} />

        <Route path="violations" element={<CarViolationList />} />
        <Route path="violations/add" element={<AddCarViolation />} />
        <Route path="violations/edit/:violationId" element={<EditCarViolation />} />
        <Route path="violations/view/:violationId" element={<ViewCarViolation />} />

      </Route>
    </Routes>
  )
}

export default AdminApp
