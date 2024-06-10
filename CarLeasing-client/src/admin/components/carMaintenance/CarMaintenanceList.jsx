import React, { useEffect, useMemo, useState } from 'react'
import { Link } from 'react-router-dom';

import { useMessage } from '../providers/MessageProvider'
import { adminConfig } from '../../../config/adminConfig'
import { getAllCarMaintenances, deleteCarMaintenance } from '../../../api/carMaintenanceApi';

import TablePlaceholder from "../content/table/TablePlaceholder"
import ContentTable from '../content/table/ContentTable'
import TableViewButton from '../content/table/button/TableViewButton';
import TableEditButton from '../content/table/button/TableEditButton';
import TableDeleteButton from '../content/table/button/TableDeleteButton';
import TablePaginator from '../content/table/TablePaginator';
import FilterButton from '../content/filter/FilterButton';
import FilterSelect from '../content/filter/FilterSelect';

export default function CarMaintenanceList() {
    const TABLE_HEADER = {
        id: "No.",
        licensePlate: "License Plate",
        carBrand: "Car Brand",
        model: "Model",
        carType: "Car Type",
        year: "Year",
        color: "Color",
        maintenanceDate: "Maintenance Date",
        cost: "Cost",
        description: "Description",
        operations: "Operations"
    };
    const ITEMS_PER_PAGE = adminConfig.itemsPerPage;

    const { showMessage } = useMessage();
    const [isLoading, setIsLoading] = useState(false);

    // data part
    const [carMaintenances, setCarMaintenances] = useState([]);
    // paginator data part
    const [currentPage, setCurrentPage] = useState(1);

    // select form part
    const [selectedCarBrand, setSelectedCarBrand] = useState("");
    const [selectedModel, setSelectedModel] = useState("");
    const [selectedLicensePlate, setSelectedLicensePlate] = useState("");

    // GET data from database
    useEffect(() => {
        const fetchCarMaintenances = async () => {
            setIsLoading(true);
            try {
                const result = await getAllCarMaintenances();
                console.log("CarMaintenancesList result: ", result);
                setCarMaintenances(result);
            } catch (error) {
                showMessage(error.message, adminConfig.colorEmun.danger);
            }
            setIsLoading(false);
        }
        fetchCarMaintenances();
    }, [])

    useEffect(() => {
        setCurrentPage(1);
    }, [])

    const filteredData = useMemo(() => {
        return carMaintenances.filter((item) => (
            item
        ));
    }, [carMaintenances])

    const currentData = useMemo(() => {
        const indexOfLastData = currentPage * ITEMS_PER_PAGE;
        const indexOfFirstData = indexOfLastData - ITEMS_PER_PAGE;
        return filteredData.slice(indexOfFirstData, indexOfLastData)
    }, [currentPage, filteredData, ITEMS_PER_PAGE]);

    const carBrandOptions = useMemo(() => {
        
    }, []);

    const modelOptions = useMemo(() => {

    }, []);

    const licensePlateOptions = useMemo(() => {

    }, []);

    // table part
    const tableHeaderHtml = () => {
        return Object.values(TABLE_HEADER).map((item, index) => {
            if (item === "Operations") {
                return <th className="fixed-column last-fixed-column" key={index}>{item}</th>
            } else {
                return <th key={index}>{item}</th>
            }
        })
    }

    const tableBodyHtml = () => {
        return currentData.map((item, index) => (
            <tr className="align-middle" key={item.id}>
                <td>{(currentPage - 1) * ITEMS_PER_PAGE + index + 1}</td>
                <td>{item.car.licensePlate}</td>
                <td>{item.car.carBrand.name}</td>
                <td>{item.car.model}</td>
                <td>{item.car.carType.typeName}</td>
                <td>{item.car.year}</td>
                <td>{item.car.color}</td>
                <td>{item.maintenanceDate}</td>
                <td>{item.cost}</td>
                <td>{item.description}</td>
                <td className="fixed-column last-fixed-column">
                    {<TableViewButton link={`view/${item.id}`} />}
                    {<TableEditButton link={`edit/${item.id}`} />}
                    {<TableDeleteButton deleteId={item.id} deleteHandle={handleDelete} />}
                </td>
            </tr>
        ))
    }

    const handleDelete = async (carMaintenanceId) => {
        try {
            const result = await deleteCarMaintenance(carMaintenanceId);
            if (result === "") {
                showMessage(`Car Maintenance was deleted!`, adminConfig.colorEnum.success);
                setCarMaintenances(carMaintenances.filter(item => item.id !== carMaintenanceId));
            }
        } catch (error) {
            showMessage(error.message, adminConfig.colorEmun.danger);
        }
    }

    // filter part
    const clearFilter = () => {
        // setSelectedCarBrand("");
        // setSelectedAvailable("");
        // setSelectedCarType("");
        // setSelectedFuelType("");
    }

    return (
        <>
            <section className="row">
                <div className="row col-sm-10">
                    <div className='row col-sm-10'>
                        <div className="col">
                            <FilterSelect
                                label={"Car Brand"}
                                getOptions={carBrandOptions}
                                selectedValue={selectedCarBrand}
                                onChange={(event) => { setSelectedCarBrand(event.target.value) }}
                            />
                        </div>

                        <div className="col">
                            <FilterSelect
                                className="col-auto"
                                label={"Model"}
                                getOptions={modelOptions}
                                selectedValue={selectedModel}
                                onChange={(event) => { setSelectedModel(event.target.value) }}
                            />
                        </div>

                        <div className="col">
                            <FilterSelect
                                className="col-auto"
                                label={"Car Type"}
                                getOptions={licensePlateOptions}
                                selectedValue={selectedLicensePlate}
                                onChange={(event) => { setSelectedLicensePlate(event.target.value) }}
                            />
                        </div>
                    </div>
                    <div className='col-sm-2 d-flex align-items-end'>
                        <FilterButton clearFilter={clearFilter} />
                    </div>
                </div>
                <div className="col-sm-2 d-flex justify-content-end align-items-end">
                    <Link to="add" className="btn btn-success">
                        <i className="bi bi-plus-circle pe-2"></i>
                        Add
                    </Link>
                </div>
            </section >

            {
                isLoading ? (<TablePlaceholder />) : (
                    <>
                        <ContentTable tableHeaderHtml={tableHeaderHtml} tableBodyHtml={tableBodyHtml} />
                        <TablePaginator
                            data={filteredData}
                            currentPage={currentPage}
                            setCurrentPage={setCurrentPage}
                            itemsPerPage={ITEMS_PER_PAGE}
                        />
                    </>
                )
            }
        </>
    )
}